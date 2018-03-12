# 前言

- 本节介绍了由ElasticSearch提供的Java API，所有操作都使用ES客户端来执行，所以默认就是异步的（可以使用listener监听，也可以直接返回结果）
- 此外，也可以使用Bulk来批量的执行一系列操作。
- 请注意，所有的API都是通过Java API调用的（实际上，ES也是一个基于Java的封装）

> 警告：在ES7.0中我们不建议您使用TransportClient，它将会在8.0中被移除。所以清尽量习惯并使用JavaHighLevelClient
