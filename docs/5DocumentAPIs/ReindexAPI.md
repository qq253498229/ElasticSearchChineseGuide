# 重新索引接口

查看[reindex API](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/docs-reindex.html)

```java
BulkByScrollResponse response = ReindexAction.INSTANCE.newRequestBuilder(client)
    .destination("target_index")
    //可以提供查询来过滤需要重新索引的文档
    .filter(QueryBuilders.matchQuery("category", "xzy")) 
    .get();
```