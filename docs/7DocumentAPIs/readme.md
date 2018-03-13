# 文档接口

这部分讲的是下面这些CRUD接口:

## 单文档接口

- [索引接口](Index API.md)
- [查询(通过主键)接口](Get API.md)
- [删除接口](Delete API.md)
- [根据条件删除接口](Delete By Query API.md)
- [修改接口](Update API.md)

## 多文档接口

- [批量查询接口](Multi Get API.md)
- [批处理接口](Bulk API.md)
- [使用批处理处理器](Using Bulk Processor.md)

> 所有的CRUD接口都是针对同一个索引的接口。
**index** 参数只可以接受单独的索引名，也可以使用**alias**来指向这个索引