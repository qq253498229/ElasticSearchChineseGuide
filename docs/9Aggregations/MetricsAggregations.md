# 指标聚合

- [最小值聚合](#最小值聚合)
- [最大值聚合](#最大值聚合)
- [求和聚合](#求和聚合)
- [平均值聚合](#平均值聚合)
- [统计聚合](#统计聚合)
- [拓展统计聚合](#拓展统计聚合)
- [值计数聚合](#值计数聚合)
- [百分比聚合](#百分比聚合)
- [百分比等级聚合](#百分比等级聚合)
- [基数聚合](#基数聚合)
- [地理界限聚合](#地理界限聚合)
- [热门聚合](#热门聚合)
- [脚本指标聚合](#脚本指标聚合)

## 最小值聚合

你可以通过JavaAPI使用[最小值聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-min-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
MinAggregationBuilder aggregation =
        AggregationBuilders
                .min("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.min.Min;
```

```java
// 这里的sr是SearchResponse对象
Min agg = sr.getAggregations().get("agg");
double value = agg.getValue();
```

## 最大值聚合

你可以通过JavaAPI使用[最大值聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-max-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
MaxAggregationBuilder aggregation =
        AggregationBuilders
                .max("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.max.Max;
```

```java
// 这里的sr是SearchResponse对象
Max agg = sr.getAggregations().get("agg");
double value = agg.getValue();
```


## 求和聚合

你可以通过JavaAPI使用[求和聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-sum-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
SumAggregationBuilder aggregation =
        AggregationBuilders
                .sum("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
```

```java
// 这里的sr是SearchResponse对象
Sum agg = sr.getAggregations().get("agg");
double value = agg.getValue();
```

## 平均值聚合

你可以通过JavaAPI使用[平均值聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-avg-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
AvgAggregationBuilder aggregation =
        AggregationBuilders
                .avg("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
```

```java
// 这里的sr是SearchResponse对象
Avg agg = sr.getAggregations().get("agg");
double value = agg.getValue();
```

## 统计聚合

你可以通过JavaAPI使用[统计聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-stats-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
StatsAggregationBuilder aggregation =
        AggregationBuilders
                .stats("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
```

```java
// 这里的sr是SearchResponse对象
Stats agg = sr.getAggregations().get("agg");
//最小值
double min = agg.getMin();
//最大值
double max = agg.getMax();
//平均值
double avg = agg.getAvg();
//求和
double sum = agg.getSum();
//数量
long count = agg.getCount();
```

## 拓展统计聚合

你可以通过JavaAPI使用[拓展统计聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-extendedstats-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
ExtendedStatsAggregationBuilder aggregation =
        AggregationBuilders
                .extendedStats("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
```

```java
// 这里的sr是SearchResponse对象
ExtendedStats agg = sr.getAggregations().get("agg");
double min = agg.getMin();
double max = agg.getMax();
double avg = agg.getAvg();
double sum = agg.getSum();
long count = agg.getCount();
//标准偏差
double stdDeviation = agg.getStdDeviation();
//平方和
double sumOfSquares = agg.getSumOfSquares();
//方差
double variance = agg.getVariance();
```

## 值计数聚合

你可以通过JavaAPI使用[值计数(value count)聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-valuecount-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
ValueCountAggregationBuilder aggregation =
        AggregationBuilders
                .count("agg")
                .field("height");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
```

```java
// sr is here your SearchResponse object
ValueCount agg = sr.getAggregations().get("agg");
long value = agg.getValue();
```

## 百分比聚合

你可以通过JavaAPI使用[百分比聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-percentile-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
PercentilesAggregationBuilder aggregation =
        AggregationBuilders
                .percentiles("agg")
                .field("height");
```

可以使用你自己的百分比数来代替默认的：

```java
PercentilesAggregationBuilder aggregation =
        AggregationBuilders
                .percentiles("agg")
                .field("height")
                .percentiles(1.0, 5.0, 10.0, 20.0, 30.0, 75.0, 95.0, 99.0);
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
```

```java
// sr is here your SearchResponse object
Percentiles agg = sr.getAggregations().get("agg");
// For each entry
for (Percentile entry : agg) {
    double percent = entry.getPercent();    // Percent
    double value = entry.getValue();        // Value

    logger.info("percent [{}], value [{}]", percent, value);
}
```

可能的返回结果：

```java
percent [1.0], value [0.814338896154595]
percent [5.0], value [0.8761912455821302]
percent [25.0], value [1.173346540141847]
percent [50.0], value [1.5432023318692198]
percent [75.0], value [1.923915462033674]
percent [95.0], value [2.2273644908535335]
percent [99.0], value [2.284989339108279]
```

## 百分比等级聚合

你可以通过JavaAPI使用[百分比等级聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-percentile-rank-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
PercentileRanksAggregationBuilder aggregation =
        AggregationBuilders
                .percentileRanks("agg")
                .field("height")
                .values(1.24, 1.91, 2.22);
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentileRanks;
```

```java
// sr is here your SearchResponse object
PercentileRanks agg = sr.getAggregations().get("agg");
// For each entry
for (Percentile entry : agg) {
    double percent = entry.getPercent();    // Percent
    double value = entry.getValue();        // Value

    logger.info("percent [{}], value [{}]", percent, value);
}
```

可能的返回结果：

```java
percent [29.664353095090945], value [1.24]
percent [73.9335313461868], value [1.91]
percent [94.40095147327283], value [2.22]
```

## 基数聚合

你可以通过JavaAPI使用[基数聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-cardinality-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
CardinalityAggregationBuilder aggregation =
        AggregationBuilders
                .cardinality("agg")
                .field("tags");
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
```

```java
// sr is here your SearchResponse object
Cardinality agg = sr.getAggregations().get("agg");
long value = agg.getValue();
```

## 地理界限聚合

你可以通过JavaAPI使用[地理界限聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-geobounds-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
GeoBoundsBuilder aggregation =
        GeoBoundsAggregationBuilder
                .geoBounds("agg")
                .field("address.location")
                .wrapLongitude(true);
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBounds;
```

```java
// sr is here your SearchResponse object
GeoBounds agg = sr.getAggregations().get("agg");
GeoPoint bottomRight = agg.bottomRight();
GeoPoint topLeft = agg.topLeft();
logger.info("bottomRight {}, topLeft {}", bottomRight, topLeft);
```

可能的返回结果：

```java
bottomRight [40.70500764381921, 13.952946866893775], topLeft [53.49603022435221, -4.190029308156676]
```

## 热门聚合

你可以通过JavaAPI使用[热门聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-top-hits-aggregation.html)。

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
AggregationBuilder aggregation =
    AggregationBuilders
        .terms("agg").field("gender")
        .subAggregation(
            AggregationBuilders.topHits("top")
        );
```

大多数在查询时使用的参数在这里都可以使用，比如 **from** , **size** , **sort** , **highlight** , **explain** ...

```java
AggregationBuilder aggregation =
    AggregationBuilders
        .terms("agg").field("gender")
        .subAggregation(
            AggregationBuilders.topHits("top")
                .explain(true)
                .size(1)
                .from(10)
        );
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
```

```java
// sr is here your SearchResponse object
Terms agg = sr.getAggregations().get("agg");

// For each entry
for (Terms.Bucket entry : agg.getBuckets()) {
    String key = entry.getKey();                    // bucket key
    long docCount = entry.getDocCount();            // Doc count
    logger.info("key [{}], doc_count [{}]", key, docCount);

    // We ask for top_hits for each bucket
    TopHits topHits = entry.getAggregations().get("top");
    for (SearchHit hit : topHits.getHits().getHits()) {
        logger.info(" -> id [{}], _source [{}]", hit.getId(), hit.getSourceAsString());
    }
}
```

可能的返回结果：

```java
key [male], doc_count [5107]
 -> id [AUnzSZze9k7PKXtq04x2], _source [{"gender":"male",...}]
 -> id [AUnzSZzj9k7PKXtq04x4], _source [{"gender":"male",...}]
 -> id [AUnzSZzl9k7PKXtq04x5], _source [{"gender":"male",...}]
key [female], doc_count [4893]
 -> id [AUnzSZzM9k7PKXtq04xy], _source [{"gender":"female",...}]
 -> id [AUnzSZzp9k7PKXtq04x8], _source [{"gender":"female",...}]
 -> id [AUnzSZ0W9k7PKXtq04yS], _source [{"gender":"female",...}]
```

## 脚本指标聚合

你可以通过JavaAPI使用[脚本指标聚合](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-aggregations-metrics-scripted-metric-aggregation.html)。

如果你想在嵌入式数据节点(例如单元测试)中使用Groovy脚本都话，不要忘了在classpath中加入groovy。例如，在maven中，你可以将下面的依赖加入到 **pom.xml** 中：

```xml
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.3.2</version>
    <classifier>indy</classifier>
</dependency>
```

### 准备聚合请求

下面是如何创建聚合请求的例子：

```java
ScriptedMetricAggregationBuilder aggregation = AggregationBuilders
    .scriptedMetric("agg")
    .initScript(new Script("params._agg.heights = []"))
    .mapScript(new Script("params._agg.heights.add(doc.gender.value == 'male' ? doc.height.value : -1.0 * doc.height.value)"));
```

您还可以指定将在每个分片上执行的 **combine** 脚本：

```java
ScriptedMetricAggregationBuilder aggregation = AggregationBuilders
    .scriptedMetric("agg")
    .initScript(new Script("params._agg.heights = []"))
    .mapScript(new Script("params._agg.heights.add(doc.gender.value == 'male' ? doc.height.value : -1.0 * doc.height.value)"))
    .combineScript(new Script("double heights_sum = 0.0; for (t in params._agg.heights) { heights_sum += t } return heights_sum"));
```

您还可以指定将在每个分片上执行的 **reduce** 脚本：

```java
ScriptedMetricAggregationBuilder aggregation = AggregationBuilders
    .scriptedMetric("agg")
    .initScript(new Script("params._agg.heights = []"))
    .mapScript(new Script("params._agg.heights.add(doc.gender.value == 'male' ? doc.height.value : -1.0 * doc.height.value)"))
    .combineScript(new Script("double heights_sum = 0.0; for (t in params._agg.heights) { heights_sum += t } return heights_sum"))
    .reduceScript(new Script("double heights_sum = 0.0; for (a in params._aggs) { heights_sum += a } return heights_sum"));
```

### 获得聚合响应

在类中引入聚合定义类：

```java
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
```

```java
// sr is here your SearchResponse object
ScriptedMetric agg = sr.getAggregations().get("agg");
Object scriptedResult = agg.aggregation();
logger.info("scriptedResult [{}]", scriptedResult);
```

请注意，聚合的结果取决于您构建的脚本。
第一个例子可能的返回结果：

```java
scriptedResult object [ArrayList]
scriptedResult [ {
"heights" : [ 1.122218480146643, -1.8148918111233887, -1.7626731575142909, ... ]
}, {
"heights" : [ -0.8046067304119863, -2.0785486707864553, -1.9183567430207953, ... ]
}, {
"heights" : [ 2.092635728868694, 1.5697545960886536, 1.8826954461968808, ... ]
}, {
"heights" : [ -2.1863201099468403, 1.6328549117346856, -1.7078288405893842, ... ]
}, {
"heights" : [ 1.6043904836424177, -2.0736538674414025, 0.9898266674373053, ... ]
} ]
```

第二个例子可能的返回结果：

```java
scriptedResult object [ArrayList]
scriptedResult [-41.279615707402876,
                -60.88007362339038,
                38.823270659734256,
                14.840192739445632,
                11.300902755741326]
```

最后一个例子可能的返回结果：

```java
scriptedResult object [Double]
scriptedResult [2.171917696507009]
```