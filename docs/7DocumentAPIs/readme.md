# 文档接口

这部分讲的是这些CRUD接口:

## 单文档接口

- [Index接口](IndexAPI.md)
- [Get接口](GetAPI.md)
- [Delete接口](DeleteAPI.md)
- [Delete By Query接口](DeleteByQueryAPI.md)
- [Update接口](UpdateAPI.md)

## 多文档接口

- [Multi Get接口](MultiGetAPI.md)
- [Bulk接口](BulkAPI.md)
- [使用Bulk Processor](UsingBulkProcessor.md)

> 所有的CRUD接口都是针对同一个索引的接口。**index** 参数只可以接受单独的索引名，也可以使用**alias**来指向这个索引