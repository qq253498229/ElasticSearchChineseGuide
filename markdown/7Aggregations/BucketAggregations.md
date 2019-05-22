# 桶聚合

- [整体聚合](#整体聚合)
- [过滤聚合](#过滤聚合)
- [多过滤器聚合](#多过滤器聚合)
- [无匹配聚合](#无匹配聚合)
- [嵌套聚合](#嵌套聚合)
- [反向嵌套聚合](#反向嵌套聚合)
- [子对象聚合](#子对象聚合)
- [匹配聚合](#匹配聚合)
- [重要匹配聚合](#重要匹配聚合)
- [范围聚合](#范围聚合)
- [日期范围聚合](#日期范围聚合)
- [Ip范围聚合](#Ip范围聚合)
- [柱状图聚合](#柱状图聚合)
- [日期柱状图聚合](#日期柱状图聚合)
- [地理距离聚合](#地理距离聚合)
- [地理哈希网格聚合](#地理哈希网格聚合)

## 整体聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-global-aggregation.html)文档。

### 准备请求

```java
AggregationBuilders
    .global("agg")
    .subAggregation(AggregationBuilders.terms("genders").field("gender"));
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.global.Global;
```

```java
// sr is here your SearchResponse object
Global agg = sr.getAggregations().get("agg");
agg.getDocCount(); // Doc count
```

## 过滤聚合

### 准备请求

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-filter-aggregation.html)文档。

```java
AggregationBuilders
    .filter("agg", QueryBuilders.termQuery("gender", "male"));
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
```

```java
// sr is here your SearchResponse object
Filter agg = sr.getAggregations().get("agg");
agg.getDocCount(); // Doc count
```

## 多过滤器聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-filters-aggregation.html)文档。

### 准备请求

```java
AggregationBuilder aggregation =
    AggregationBuilders
        .filters("agg",
            new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("gender", "male")),
            new FiltersAggregator.KeyedFilter("women", QueryBuilders.termQuery("gender", "female")));
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
```

```java
// sr is here your SearchResponse object
Filters agg = sr.getAggregations().get("agg");

// For each entry
for (Filters.Bucket entry : agg.getBuckets()) {
    String key = entry.getKeyAsString();            // bucket key
    long docCount = entry.getDocCount();            // Doc count
    logger.info("key [{}], doc_count [{}]", key, docCount);
}
```

返回值：

```java
key [men], doc_count [4982]
key [women], doc_count [5018]
```

## 无匹配聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-missing-aggregation.html)文档。

### 准备请求

```java
AggregationBuilders.missing("agg").field("gender");
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
```

```java
// sr is here your SearchResponse object
Missing agg = sr.getAggregations().get("agg");
agg.getDocCount(); // Doc count
```

## 嵌套聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-nested-aggregation.html)文档。

### 准备请求

```java
AggregationBuilders
    .nested("agg", "resellers");
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
```

```java
// sr is here your SearchResponse object
Nested agg = sr.getAggregations().get("agg");
agg.getDocCount(); // Doc count
```

## 反向嵌套聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-reverse-nested-aggregation.html)文档。

### 准备请求

```java
AggregationBuilder aggregation =
    AggregationBuilders
        .nested("agg", "resellers")
        .subAggregation(
                AggregationBuilders
                        .terms("name").field("resellers.name")
                        .subAggregation(
                                AggregationBuilders
                                        .reverseNested("reseller_to_product")
                        )
        );
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
```

```java
// sr is here your SearchResponse object
Nested agg = sr.getAggregations().get("agg");
Terms name = agg.getAggregations().get("name");
for (Terms.Bucket bucket : name.getBuckets()) {
    ReverseNested resellerToProduct = bucket.getAggregations().get("reseller_to_product");
    resellerToProduct.getDocCount(); // Doc count
}
```

## 子对象聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-children-aggregation.html)文档。

### 准备请求

```java
AggregationBuilder aggregation =
    AggregationBuilders
        //agg是聚合名，reseller是子类型
        .children("agg", "reseller"); 
```

### 获取响应

```java
import org.elasticsearch.join.aggregations.Children;
```

```java
// sr is here your SearchResponse object
Children agg = sr.getAggregations().get("agg");
agg.getDocCount(); // Doc count
```

## 匹配聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-terms-aggregation.html)文档。

### 准备请求

```java
AggregationBuilders
    .terms("genders")
    .field("gender");
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
```

```java
// sr is here your SearchResponse object
Terms genders = sr.getAggregations().get("genders");

// For each entry
for (Terms.Bucket entry : genders.getBuckets()) {
    entry.getKey();      // Term
    entry.getDocCount(); // Doc count
}
```

### 排序

```java
import org.elasticsearch.search.aggregations.BucketOrder;
```

以生序方式按其 **doc_count** 排序：

```java
AggregationBuilders
    .terms("genders")
    .field("gender")
    .order(BucketOrder.count(true))
```

以生序方式按其字母顺序排序：

```java
AggregationBuilders
    .terms("genders")
    .field("gender")
    .order(BucketOrder.key(true))
```

通过指标子聚合排序(按汇总名称标志)：

```java
AggregationBuilders
    .terms("genders")
    .field("gender")
    .order(BucketOrder.aggregation("avg_height", false))
    .subAggregation(
        AggregationBuilders.avg("avg_height").field("height")
    )
```

按多个条件排序：

```java
AggregationBuilders
    .terms("genders")
    .field("gender")
    .order(BucketOrder.compound( // in order of priority:
        BucketOrder.aggregation("avg_height", false), // sort by sub-aggregation first
        BucketOrder.count(true))) // then bucket count as a tie-breaker
    .subAggregation(
        AggregationBuilders.avg("avg_height").field("height")
    )
```

## 重要匹配聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-significantterms-aggregation.html)文档。

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .significantTerms("significant_countries")
                .field("address.country");

// Let say you search for men only
SearchResponse sr = client.prepareSearch()
        .setQuery(QueryBuilders.termQuery("gender", "male"))
        .addAggregation(aggregation)
        .get();
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
```

```java
// sr is here your SearchResponse object
SignificantTerms agg = sr.getAggregations().get("significant_countries");

// For each entry
for (SignificantTerms.Bucket entry : agg.getBuckets()) {
    entry.getKey();      // Term
    entry.getDocCount(); // Doc count
}
```

## 范围聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-range-aggregation.html)文档。

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .range("agg")
                .field("height")
                .addUnboundedTo(1.0f)               // from -infinity to 1.0 (excluded)
                .addRange(1.0f, 1.5f)               // from 1.0 to 1.5 (excluded)
                .addUnboundedFrom(1.5f);            // from 1.5 to +infinity
```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.range.Range;
```

```java// sr is here your SearchResponse object
       Range agg = sr.getAggregations().get("agg");
       
       // For each entry
       for (Range.Bucket entry : agg.getBuckets()) {
           String key = entry.getKeyAsString();             // Range as key
           Number from = (Number) entry.getFrom();          // Bucket from
           Number to = (Number) entry.getTo();              // Bucket to
           long docCount = entry.getDocCount();    // Doc count
       
           logger.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
       }

```

返回值：

```java
key [*-1.0], from [-Infinity], to [1.0], doc_count [9]
key [1.0-1.5], from [1.0], to [1.5], doc_count [21]
key [1.5-*], from [1.5], to [Infinity], doc_count [20]
```

## 日期范围聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-daterange-aggregation.html)

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .dateRange("agg")
                .field("dateOfBirth")
                .format("yyyy")
                .addUnboundedTo("1950")    // from -infinity to 1950 (excluded)
                .addRange("1950", "1960")  // from 1950 to 1960 (excluded)
                .addUnboundedFrom("1960"); // from 1960 to +infinity

```

### 获取响应

```java
import org.elasticsearch.search.aggregations.bucket.range.Range;

```

```java
// sr is here your SearchResponse object
Range agg = sr.getAggregations().get("agg");

// For each entry
for (Range.Bucket entry : agg.getBuckets()) {
    String key = entry.getKeyAsString();                // Date range as key
    DateTime fromAsDate = (DateTime) entry.getFrom();   // Date bucket from as a Date
    DateTime toAsDate = (DateTime) entry.getTo();       // Date bucket to as a Date
    long docCount = entry.getDocCount();                // Doc count

    logger.info("key [{}], from [{}], to [{}], doc_count [{}]", key, fromAsDate, toAsDate, docCount);
}

```

返回值：

```java
key [*-1950], from [null], to [1950-01-01T00:00:00.000Z], doc_count [8]
key [1950-1960], from [1950-01-01T00:00:00.000Z], to [1960-01-01T00:00:00.000Z], doc_count [5]
key [1960-*], from [1960-01-01T00:00:00.000Z], to [null], doc_count [37]
```

## Ip范围聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-iprange-aggregation.html)

### 准备请求

```java
AggregatorBuilder<?> aggregation =
        AggregationBuilders
                .ipRange("agg")
                .field("ip")
                .addUnboundedTo("192.168.1.0")             // from -infinity to 192.168.1.0 (excluded)
                .addRange("192.168.1.0", "192.168.2.0")    // from 192.168.1.0 to 192.168.2.0 (excluded)
                .addUnboundedFrom("192.168.2.0");          // from 192.168.2.0 to +infinity
```

注意，你也可以使用ip掩码作为范围：

```java
AggregatorBuilder<?> aggregation =
        AggregationBuilders
                .ipRange("agg")
                .field("ip")
                .addMaskRange("192.168.0.0/32")
                .addMaskRange("192.168.0.0/24")
                .addMaskRange("192.168.0.0/16");
```

### 获取响应


```java
import org.elasticsearch.search.aggregations.bucket.range.Range;
```

```java
// sr is here your SearchResponse object
Range agg = sr.getAggregations().get("agg");

// For each entry
for (Range.Bucket entry : agg.getBuckets()) {
    String key = entry.getKeyAsString();            // Ip range as key
    String fromAsString = entry.getFromAsString();  // Ip bucket from as a String
    String toAsString = entry.getToAsString();      // Ip bucket to as a String
    long docCount = entry.getDocCount();            // Doc count

    logger.info("key [{}], from [{}], to [{}], doc_count [{}]", key, fromAsString, toAsString, docCount);
}
```

第一个请求的返回值：

```java
key [*-192.168.1.0], from [null], to [192.168.1.0], doc_count [13]
key [192.168.1.0-192.168.2.0], from [192.168.1.0], to [192.168.2.0], doc_count [14]
key [192.168.2.0-*], from [192.168.2.0], to [null], doc_count [23]
```

第二个：

```java
key [192.168.0.0/32], from [192.168.0.0], to [192.168.0.1], doc_count [0]
key [192.168.0.0/24], from [192.168.0.0], to [192.168.1.0], doc_count [13]
key [192.168.0.0/16], from [192.168.0.0], to [192.169.0.0], doc_count [50]
```

## 柱状图聚合


点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-histogram-aggregation.html)

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .histogram("agg")
                .field("height")
                .interval(1);
```

### 获取响应


```java
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
```

```java
// sr is here your SearchResponse object
Histogram agg = sr.getAggregations().get("agg");

// For each entry
for (Histogram.Bucket entry : agg.getBuckets()) {
    Number key = (Number) entry.getKey();   // Key
    long docCount = entry.getDocCount();    // Doc count

    logger.info("key [{}], doc_count [{}]", key, docCount);
}
```

### 排序

支持与[TermsAggregation](#TermsAggregation)相同的排序功能。

## 日期柱状图聚合


点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-datehistogram-aggregation.html)

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .dateHistogram("agg")
                .field("dateOfBirth")
                .dateHistogramInterval(DateHistogramInterval.YEAR);
```

如果你想设置10天间隔：

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .dateHistogram("agg")
                .field("dateOfBirth")
                .dateHistogramInterval(DateHistogramInterval.days(10));
```

### 获取响应


```java
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
```

```java
// sr is here your SearchResponse object
Histogram agg = sr.getAggregations().get("agg");

// For each entry
for (Histogram.Bucket entry : agg.getBuckets()) {
    DateTime key = (DateTime) entry.getKey();    // Key
    String keyAsString = entry.getKeyAsString(); // Key as String
    long docCount = entry.getDocCount();         // Doc count

    logger.info("key [{}], date [{}], doc_count [{}]", keyAsString, key.getYear(), docCount);
}
```

第一个请求的返回值：

```java
key [1942-01-01T00:00:00.000Z], date [1942], doc_count [1]
key [1945-01-01T00:00:00.000Z], date [1945], doc_count [1]
key [1946-01-01T00:00:00.000Z], date [1946], doc_count [1]
...
key [2005-01-01T00:00:00.000Z], date [2005], doc_count [1]
key [2007-01-01T00:00:00.000Z], date [2007], doc_count [2]
key [2008-01-01T00:00:00.000Z], date [2008], doc_count [3]
```

### 排序

支持与[TermsAggregation](#TermsAggregation)相同的排序功能。

## 地理距离聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-geodistance-aggregation.html)

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .geoDistance("agg", new GeoPoint(48.84237171118314,2.33320027692004))
                .field("address.location")
                .unit(DistanceUnit.KILOMETERS)
                .addUnboundedTo(3.0)
                .addRange(3.0, 10.0)
                .addRange(10.0, 500.0);
```

### 获取响应


```java
import org.elasticsearch.search.aggregations.bucket.range.Range;
```

```java
// sr is here your SearchResponse object
Range agg = sr.getAggregations().get("agg");

// For each entry
for (Range.Bucket entry : agg.getBuckets()) {
    String key = entry.getKeyAsString();    // key as String
    Number from = (Number) entry.getFrom(); // bucket from value
    Number to = (Number) entry.getTo();     // bucket to value
    long docCount = entry.getDocCount();    // Doc count

    logger.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
}
```

返回值：

```java
key [*-3.0], from [0.0], to [3.0], doc_count [161]
key [3.0-10.0], from [3.0], to [10.0], doc_count [460]
key [10.0-500.0], from [10.0], to [500.0], doc_count [4925]
```

## 地理哈希网格聚合

点击[查看](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-aggregations-bucket-geohashgrid-aggregation.html)

### 准备请求

```java
AggregationBuilder aggregation =
        AggregationBuilders
                .geohashGrid("agg")
                .field("address.location")
                .precision(4);
```

### 获取响应


```java
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoHashGrid;
```

```java
// sr is here your SearchResponse object
GeoHashGrid agg = sr.getAggregations().get("agg");

// For each entry
for (GeoHashGrid.Bucket entry : agg.getBuckets()) {
   String keyAsString = entry.getKeyAsString(); // key as String
   GeoPoint key = (GeoPoint) entry.getKey();    // key as geo point
   long docCount = entry.getDocCount();         // Doc count

   logger.info("key [{}], point {}, doc_count [{}]", keyAsString, key, docCount);
}
```

返回值：

```java
key [gbqu], point [47.197265625, -1.58203125], doc_count [1282]
key [gbvn], point [50.361328125, -4.04296875], doc_count [1248]
key [u1j0], point [50.712890625, 7.20703125], doc_count [1156]
key [u0j2], point [45.087890625, 7.55859375], doc_count [1138]
...
```