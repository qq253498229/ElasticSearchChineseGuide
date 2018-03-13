# 名词解释(对比关系型数据库)

- index: 索引(名词)，类似于关系型数据库(例如MySQL/SQLServer等)中的数据库(database)
- type: 类型，类似于关系型数据库中的表格(table)
- document: 文档，类似于关系型数据库中的一条数据
- id: 唯一标示，类似于关系型数据库中的主键

- index: 索引(动词)，类似于关系型数据库中的insert
- get: 查询，类似于关系型数据库中的select
- delete: 删除，类似于关系型数据库中的delete
- update: 更新，类似于关系型数据库中的update

- bulk:批处理接口的关键字，在实际项目中你可能会经常用到它，类似于关系型数据库中的 **insert into table values(x),(x),(x)** 这种批处理写法。当然ES还支持批量删除和修改
- JavaHighLevelClient: java高级接口，提供了更强大的功能以及更加易于拓展和维护的特性，当然很多功能目前还没人写(23333)，
期待您的[issue](https://github.com/elastic/elasticsearch/issues/27205)哦:)

> 注意，名词解释只是方便理解，实现原理和使用和关系型数据库可能有很大的区别。
所以请不要以关系型数据库的思维来学习ES