# 使用聚合

下面这段代码展示了如何在一次查询中加入两个聚合：

```java
SearchResponse sr = client.prepareSearch()
    .setQuery(QueryBuilders.matchAllQuery())
    .addAggregation(
            AggregationBuilders.terms("agg1").field("field")
    )
    .addAggregation(
            AggregationBuilders.dateHistogram("agg2")
                    .field("birth")
                    .dateHistogramInterval(DateHistogramInterval.YEAR)
    )
    .get();

// Get your facet results
Terms agg1 = sr.getAggregations().get("agg1");
Histogram agg2 = sr.getAggregations().get("agg2");
```

查看[Aggregations Java API ](../9Aggregations/readme.md)详细文档