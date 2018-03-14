# 构建聚合

如[Aggregations guide](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations.html)([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/cn/aggregations.html))所述，你可以在聚合中定义子聚合。

聚合可以是 **指标（Metrics）** 聚合或者是 **桶（Buckets）** 聚合。

例如，这里是一个3级聚合，分别由下面这些部分组成：

- 条件聚合(桶)
- 日期柱状图聚合(桶)
- 平均值聚合(指标)

```java
SearchResponse sr = node.client().prepareSearch()
    .addAggregation(
        AggregationBuilders.terms("by_country").field("country")
        .subAggregation(AggregationBuilders.dateHistogram("by_year")
            .field("dateOfBirth")
            .dateHistogramInterval(DateHistogramInterval.YEAR)
            .subAggregation(AggregationBuilders.avg("avg_children").field("children"))
        )
    )
    .execute().actionGet();
```