# 管理员接口

ES提供了完整的JavaAPI来处理管理任务。

想要访问它们，你需要从 **client** 中调用 **admin()** 方法来获取 **AdminClient** ：

```java
AdminClient adminClient = client.admin();
```

> 注意：在这篇文档中，我们使用 **client.admin()** 来获取。

- [管理索引接口](IndicesAdministration.md)
- [管理集群接口](ClusterAdministration.md)