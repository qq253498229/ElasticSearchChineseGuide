# 组合查询

组合查询可以将其它组合查询，以及其它单独的查询语句合并到一起，并将结果和匹配分数组合起来。它可以改变它们的行为，或是将查询转换为过滤条件。

这一组中的查询有：

[constant_score查询](#constant_score查询)

- 包含其它查询的查询，但却以过滤器的形式执行该语句。所有匹配文档的匹配分数相同。

[bool查询](bool查询)

- 使用 **must** 、 **should** 、 **must_not** 或 **filter** 将多个查询或组合查询语句合并到一起。
其中 **must** 和 **should** 的得分会合并到一起，而 **must_not** 和 **filter**则在过滤器中执行。

[dis_max查询](dis_max查询)

- 一个接受多个查询的查询，并返回任何查询子句匹配的文档。

[function_score查询](function_score查询)

- 为了考虑到流行度、活跃度、距离和自定义脚本实现的算法，而使用函数修改主查询返回的分数。

[boosting查询](boosting查询)

- 这里查询条件将会分为正匹配和负匹配。正匹配到的文档将会加分，负匹配得到的文档将会减分。

## constant_score查询

查看[Constant Score Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-constant-score-query.html)

```java
constantScoreQuery(
        termQuery("name","kimchy"))                          
    .boost(2.0f);  
```