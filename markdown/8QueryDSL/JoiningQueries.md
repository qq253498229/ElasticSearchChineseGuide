# 关联关系查询

在ES这样的分布式系统中使用SQL风格的关联语句对性能的损耗是非常大的。所以，ES提供了两种支持水平拓展的关联操作。

[nested查询](#Nested查询)

 - 文档可能包含 **nested** 类型的字段。这些字段用于索引对象数组，其中每个对象都可以作为独立文档进行查询(使用 **nested** 查询)。
 
[has_child](#HasChild查询)和[has_parent](#HasParent查询)查询
 
 - 同一索引之中的文档可以存在父子关系。 **has_child** 查询会返回子文档匹配的父文档，而 **has_parent** 查询则会返回父文档匹配的子文档。
 
## Nested查询

查看[Nested Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-nested-query.html)。

```java
nestedQuery(
        //嵌套文档的路径
        "obj1",       
        //查询中使用的所有字段都必须使用完整路径
        boolQuery()                                          
                .must(matchQuery("obj1.name", "blue"))
                .must(rangeQuery("obj1.count").gt(5)),
        //评分模式支持：ScoreMode.Max、ScoreMode.Min、ScoreMode.Total、ScoreMode.Avg 和 ScoreMode.None
        ScoreMode.Avg);   
```

## HasChild查询

查看[Has Child Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-has-child-query.html)。

使用 **has_child** 查询的时候有一点很重要的就是使用 **PreBuiltTransportClient** 而不是普通的 **client**。

```java
Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
TransportClient client = new PreBuiltTransportClient(settings);
client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(InetAddresses.forString("127.0.0.1"), 9300)));
```

否则父链接模块将不会被加载，并且[TransportClient]中不能使用 **has_child** 查询。

```java
JoinQueryBuilders.hasChildQuery(
        //需要查询的子查询
        "blog_tag",                                          
        termQuery("tag","something"),                        
        //评分模式支持：ScoreMode.Avg、ScoreMode.Max、ScoreMode.Min、ScoreMode.None 和 ScoreMode.None
        ScoreMode.None);   
```


## HasParent查询

查看[Has Parent](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-has-parent-query.html)。

使用 **has_child** 查询的时候有一点很重要的就是使用 **PreBuiltTransportClient** 而不是普通的 **client**。

```java
Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
TransportClient client = new PreBuiltTransportClient(settings);
client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(InetAddresses.forString("127.0.0.1"), 9300)));
```

否则父链接模块将不会被加载，并且[TransportClient](../4Client/readme.md#TransportClient)中不能使用 **has_parent** 查询。

```java
JoinQueryBuilders.hasParentQuery(
    "blog",                                                  
    termQuery("tag","something"),  
    //是否将父文档匹配分数传递给子文档
    false);     
```


