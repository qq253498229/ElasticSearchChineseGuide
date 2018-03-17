# 全文检索
 
高级全文检索通常使用在全文本字段中运行全文查询，例如邮件的正文。ES知道如何分析所查询的字段，并在查询前将每个字段的分析器(或查询分析器)应用于查询字符串中。

这一组中的查询有：

[match查询](#match查询)

- 全文检索的标准模式，包括模糊匹配、短语匹配和近似匹配。

[multi_match匹配查询](#multi_match匹配查询)

- match查询的多字段版本。

[common_terms查询](#common_terms查询)

- 专门为不常用词准备的查询。

[query_string查询](#query_string查询)

- 支持紧凑型的Lucene字符串查询语法，允许在一个字符串中加入 AND | OR | NOT 条件和多字段查询，仅供熟练用户使用。

[simple_query_string查询](#simple_query_string查询)

- query_string查询的更精简、健全的版本，仅供熟练用户使用。

## match查询

查看[Match Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-match-query.html)

```java
QueryBuilder qb = matchQuery(
    //字段
    "name",  
    //查询值
    "kimchy elasticsearch"   
);
```

## multi_match匹配查询

查看[Multi Match Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-multi-match-query.html)

```java
QueryBuilder qb = multiMatchQuery(
    //查询值    
    "kimchy elasticsearch", 
    //字段
    "user", "message"       
);
```

## common_terms查询

查看[Common Terms Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-common-terms-query.html)

```java
QueryBuilder qb = commonTermsQuery(
    //字段
    "name",    
    //查询值
    "kimchy"); 
```

## query_string查询

查看[Query String Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-query-string-query.html)

```java
QueryBuilder qb = queryStringQuery(
    //查询值
    "+kimchy -elasticsearch");
```

## simple_query_string查询

查看[Simple Query String Query](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl-simple-query-string-query.html)

```java
QueryBuilder qb = simpleQueryStringQuery(
    //查询值    
    "+kimchy -elasticsearch");
```