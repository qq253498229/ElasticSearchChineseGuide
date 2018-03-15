# 表达式查询

ES以类似REST[Query DSL](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl.html)([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/cn/query-dsl-intro.html))的方式提供了完整的表达式查询。

可以通过 **QueryBuilders** 构建查询。构建成功后就可以使用 [Search API](../8SearchAPI/readme.md)。

使用 **QueryBuilders** 之前需要在类中引入 ：

```java
import static org.elasticsearch.index.query.QueryBuilders.*;
```

注意你可以使用 **QueryBuilder** 类中的 **toString()** 方法轻松的打印(调试)JSON生成的查询语句。

**QueryBuilder** 可以被用于接受查询的任何接口中，例如 **count** 和 **search** 。

- [匹配所有的查询](MatchAllQuery.md)
- [全文检索](FullTextQueries.md)
- [匹配查询](TermLevelQueries.md)
- [复合查询](CompoundQueries.md)
- [关联关系查询](JoiningQueries.md)
- [地理位置查询](GeoQueries.md)
- [专业查询](SpecializedQueries.md)
- [跨度查询](SpanQueries.md)
