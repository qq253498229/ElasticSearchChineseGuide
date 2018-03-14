# 批量查询

点击[这里](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-multi-search.html)查看批量查询的文档。

```java
SearchRequestBuilder srb1 = client
    .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
SearchRequestBuilder srb2 = client
    .prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

MultiSearchResponse sr = client.prepareMultiSearch()
        .add(srb1)
        .add(srb2)
        .get();

// You will get all individual responses from MultiSearchResponse#getResponses()
long nbHits = 0;
for (MultiSearchResponse.Item item : sr.getResponses()) {
    SearchResponse response = item.getResponse();
    nbHits += response.getHits().getTotalHits();
}
```