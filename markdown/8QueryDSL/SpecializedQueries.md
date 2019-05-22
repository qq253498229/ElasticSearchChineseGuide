# 特殊查询

[more_like_this](#MoreLikeThis查询)

- 查找与指定文本、文档或集合相似的文档。

[script](#Script查询)

- 可以使用脚本作为过滤器来查询，详情请查看[function_score查询](CompoundQueries.md#function_score查询)。

[percolate](#Percolate查询)

- 通过文档查询过滤器。

[wrapper](#Wrapper查询)

- 可以接受Json和Yaml类型语句的查询。

## MoreLikeThis查询

查看[More Like This Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-mlt-query.html)

```java
String[] fields = {"name.first", "name.last"};               
String[] texts = {"text like this one"};                     

moreLikeThisQuery(fields, texts, null)
    .minTermFreq(1)                                          
    .maxQueryTerms(12);  
```

## Script查询

查看[Script Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-script-query.html)

```java
scriptQuery(
        new Script("doc['num1'].value > 1")                  
    );
```

如果数据节点上已经存储脚本，例如叫 **myscript.painless** ：

```java
doc['num1'].value > params.param1
```

这是就可以使用：

```java
Map<String, Object> parameters = new HashMap<>();
parameters.put("param1", 5);
scriptQuery(new Script(
        //脚本类型：ScriptType.FILE、ScriptType.INLINE或ScriptType.INDEXED
        ScriptType.STORED,                                   
        //脚本引擎
        null,                                          
        //脚本名
        "myscript",                                          
        //类型是Map<String, Object>
        singletonMap("param1", 5)));   
```

## Percolate查询

查看[Percolate Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-percolate-query.html)

```java
Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
TransportClient client = new PreBuiltTransportClient(settings);
client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(InetAddresses.forString("127.0.0.1"), 9300)));
```

在使用 **percolate** 查询之前，应该添加 **percolate** 的映射，并且应该对包含 **percolate** 查询的文档建立索引：

```java
// create an index with a percolator field with the name 'query':
client.admin().indices().prepareCreate("myIndexName")
                        .addMapping("query", "query", "type=percolator")
                        .addMapping("docs", "content", "type=text")
                        .get();

//This is the query we're registering in the percolator
QueryBuilder qb = termQuery("content", "amazing");

//Index the query = register it in the percolator
client.prepareIndex("myIndexName", "query", "myDesignatedQueryName")
    .setSource(jsonBuilder()
        .startObject()
            .field("query", qb) // Register the query
        .endObject())
    .setRefreshPolicy(RefreshPolicy.IMMEDIATE) // Needed when the query shall be available immediately
    .get();
```

查询语句在 **myDesignatedQueryName** 的下面。

为了根据以注册的查询检查文档，请使用：

```java
//Build a document to check against the percolator
XContentBuilder docBuilder = XContentFactory.jsonBuilder().startObject();
docBuilder.field("content", "This is amazing!");
docBuilder.endObject(); //End of the JSON root object

PercolateQueryBuilder percolateQuery = new PercolateQueryBuilder("query", "docs", docBuilder.bytes());

// Percolate, by executing the percolator query in the query dsl:
SearchResponse response = client().prepareSearch("myIndexName")
        .setQuery(percolateQuery))
        .get();
//Iterate over the results
for(SearchHit hit : response.getHits()) {
    // Percolator queries as hit
}
```

## Wrapper查询

查看[Wrapper Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-wrapper-query.html)

```java
//在query builder中定义查询
String query = "{\"term\": {\"user\": \"kimchy\"}}"; 
wrapperQuery(query);
```
