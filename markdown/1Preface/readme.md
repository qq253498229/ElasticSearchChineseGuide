# 前言

本节介绍了由ElasticSearch(之后简称为ES)提供的Java API，所有操作都使用[Client](../4Client/readme.md)来执行，所有请求默认是异步的（可以使用listener监听，也可以直接返回结果）

此外，也可以使用[Bulk](../5DocumentAPIs/BulkAPI.md)来批量的执行一系列操作。

请注意，所有的API都是通过Java API调用的（实际上，ES也是一个基于Java的封装）

> 警告：在ES7.0中我们不建议您使用TransportClient，它将会在8.0中被移除。所以请尽量快速地习惯并使用[JavaHighLevelClient]()，它执行的是HTTP请求而不是序列化之后的Java请求。
[迁移指南](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/5.6/java-rest-high-level-migration.html)中介绍了所需的所有步骤。

> JavaHighLevelClient现在支持了更多常用的接口，但还有许多需要添加。点击[这里](https://github.com/elastic/elasticsearch/issues/27205)通知我们您需要哪些缺失的接口，帮助我们完善ES。

> 您也可以直接使用带有JSON请求体的[low level Java REST Client]()请求来获取响应。
