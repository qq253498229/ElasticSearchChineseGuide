# 术语级别查询

尽管全文查询将在执行之前分析查询语句，但术语级别查询会按照存储在倒排索引中的确切术语进行操作。

这些查询语句通常使用数字、日期和枚举等结构化数据，而不是全字符串字段。并且也允许在分析过程之前使用低级查询。

这一组中的查询有：

[term查询](#term查询)

- 根据指定词查询文档。

[terms查询](#terms查询)

- 根据多个指定词查询文档。

[range查询](#range查询)

- 根据范围(日期、数字或字符串)查询文档。

[exists查询](#exists查询)

- 根据字段是否存在查询文档。

[prefix查询](#prefix查询)

- 根据前缀查询文档。

[wildcard查询](#wildcard查询)

- 根据通配符查询文档，支持单字符匹配(?)和多字符匹配(*)。

[regexp查询](#regexp查询)

- 根据正则表达式查询文档。

[fuzzy查询](#fuzzy查询)

- 根据模糊相似词查询文档，模糊度的标准是[莱文斯坦距离](https://baike.baidu.com/item/%E8%8E%B1%E6%96%87%E6%96%AF%E5%9D%A6%E8%B7%9D%E7%A6%BB/14448097?fr=aladdin)1或2。

[type查询](#type查询)

- 根据type查询文档。

[ids查询](#ids查询)

- 根据type和IDs查询文档。

## term查询

查看[Term Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-term-query.html)

```java
QueryBuilder qb = termQuery(
    //字段名
    "name",   
    //查询值
    "kimchy"   
);
```

## terms查询

查看[Terms Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-terms-query.html)

```java
QueryBuilder qb = termsQuery(
    //字段名
    "tags",
    //查询值
    "blue", "pill");  
```

## range查询

查看[Range Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-range-query.html)

```java
QueryBuilder qb = rangeQuery(
    //字段名
    "price")   
    //最小值
    .from(5) 
    //最大值                           
    .to(10)        
    //是否包含最小值                     
    .includeLower(true)                 
    //是否包含最大值
    .includeUpper(false); 
```

```java
// 一个简单写法，lt小于，ltd小于等于；gt大于，gte大于等于
QueryBuilder qb = rangeQuery("age")   
    .gte("10")                        
    .lt("20");        
```