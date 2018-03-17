# Search接口

Search接口允许执行一个查询操作，并返回与查询条件相匹配的结果。允许跨index查询，也可以跨type查询。
可以使用[query Java API](https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/java-query-dsl.html)作为查询语句。
查询的请求体使用**SearchSourceBuilder**构建。例：

```java
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
```

```java
SearchResponse response = client.prepareSearch("index1", "index2")
        .setTypes("type1", "type2")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
        .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
        .setFrom(0).setSize(60).setExplain(true)
        .get();
```

注意所有的参数都不是必填的，下面的例子就是一个最小的查询请求：

```java
// 使用默认的参数，查询所有集群的所有文档。
SearchResponse response = client.prepareSearch().get();
```

> 注意：尽管JavaAPI定义了额外的搜索类型 **QUERY_AND_FETCH** 和 **DFS_QUERY_AND_FETCH** ，但这些模式其实是底层自动优化时使用的模式，不应该由用户指定。

有关索引操作的更多信息，请查阅REST[search](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search.html)文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/search.html))。

- [使用游标查询](UsingScrollsInJava.md)
- [批量查询](MultiSearchAPI.md)
- [使用聚合](UsingAggregations.md)
- [设置终止标志](TerminateAfter.md)
- [模版查询](SearchTemplate.md)