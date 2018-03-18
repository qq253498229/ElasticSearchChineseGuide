# 跨度查询

[span_term](#SpanTerm查询)

- 等同于[term查询](TermLevelQueries.md#term查询)，但用于其它跨度查询。
 
[span_multi](#SpanMulti查询)

- 包括[term](TermLevelQueries.md#term查询)、[range](TermLevelQueries.md#range查询)、[prefix](TermLevelQueries.md#prefix查询)、[wildcard](TermLevelQueries.md#wildcard查询)、[regexp](TermLevelQueries.md#regexp查询)或[fuzzy](TermLevelQueries.md#fuzzy查询)。
 
[span_first](#SpanFirst查询)

- 在前N个位置接受其它跨度查询。
 
[span_near](#SpanNear查询)

- 在指定距离内接受其它跨度查询，并且可以是以相同的顺序。
 
[span_or](#SpanOr查询)

- 将多个跨度查询组合起来，并返回任何匹配的文档。
 
[span_not](#SpanNot查询)

- 包含其它跨度查询，并排除匹配的文档。
 
[span_containing](#SpanContaining查询)

- 接受一个跨度查询列表，但只返回于两个条件都匹配的文档。
 
[span_within](#SpanWithin查询)

- 只要跨度落在由其它跨度范围中，就会返回一个跨度查询的结果。
 

## SpanTerm查询

查看[Span Term Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-term-query.html)

```java
spanTermQuery(
        "user",       
        "kimchy");  
```

## SpanMulti查询

查看[Span Multi Term Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-multi-term-query.html)

```java
spanMultiTermQueryBuilder(
        //支持任何继承MultiTermQueryBuilder的类。例如：FuzzyQueryBuilder、PrefixQueryBuilder、RangeQueryBuilder、RegexpQueryBuilder或WildcardQueryBuilder
        prefixQuery("user", "ki"));   
```

## SpanFirst查询

查看[Span First Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-first-query.html)

```java
spanFirstQuery(
        spanTermQuery("user", "kimchy"),                     
        //最大位置
        3                                                    
    );
```

## SpanNear查询

查看[Span Near Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-near-query.html)

```java
spanNearQuery(
        spanTermQuery("field","value1"),   
        //不匹配的最大数量
        12)                                                  
            .addClause(spanTermQuery("field","value2"))      
            .addClause(spanTermQuery("field","value3"))      
            //是否按照顺序进行匹配
            .inOrder(false);   
```

## SpanOr查询

查看[Span Or Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-or-query.html)

```java
spanOrQuery(spanTermQuery("field","value1"))                 
    .addClause(spanTermQuery("field","value2"))              
    .addClause(spanTermQuery("field","value3"));   
```

## SpanNot查询

查看[Span Not Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-not-query.html)

```java
spanNotQuery(
        spanTermQuery("field","value1"),                     
        spanTermQuery("field","value2"));    
```

## SpanContaining查询

查看[Span Containing Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-containing-query.html)

```java
spanContainingQuery(
        spanNearQuery(spanTermQuery("field1","bar"), 5)      
            .addClause(spanTermQuery("field1","baz"))
            .inOrder(true),
        spanTermQuery("field1","foo"));
```

## SpanWithin查询

查看[Span Within Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-span-within-query.html)

```java
spanWithinQuery(
        spanNearQuery(spanTermQuery("field1", "bar"), 5)     
            .addClause(spanTermQuery("field1", "baz"))
            .inOrder(true),
        spanTermQuery("field1", "foo"));   
```
