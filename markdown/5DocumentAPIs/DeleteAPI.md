# Delete接口

Delete接口可以在指定的索引中根据ID删除一个JSON类型的文档。
下面的例子就是从index是twitter的索引中删除一个JSON格式的document，它的type是tweet，id是1：

```java
DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
```

有关Get操作的更多信息，请查阅 [delete 接口](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/docs-delete.html)文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/delete-doc.html))。
