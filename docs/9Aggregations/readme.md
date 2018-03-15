# 聚合

ES提供了完整的JavaAPI来使用聚合，请查阅[Aggregations guide](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations.html)([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/cn/aggregations.html))。

使用工厂来构建聚合( **AggregationBuilders** )，并将需要计算的聚合加入到查询请求中：

```java
SearchResponse sr = node.client().prepareSearch()
        .setQuery( /* your query */ )
        .addAggregation( /* add an aggregation */ )
        .execute().actionGet();
```

注意，这里可以添加多个聚合。请查阅[Search Java API](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-search.html)。

构建聚合请求时，可以使用内置的 **AggregationBuilders** 工具。只需要在类中引入：

```java
import org.elasticsearch.search.aggregations.AggregationBuilders;
```

- [构建聚合](StructuringAggregations.md)
- [指标聚合](MetricsAggregations.md)
- [桶聚合](BucketAggregations.md)