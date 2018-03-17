# Get接口

Get接口允许根据id从index中获取JSON格式的document。
下面的例子就是从index是twitter的索引中获取一个JSON格式的document，它的type是tweet，id是1：

```java
GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
```
有关Get操作的更多信息，请查阅REST [get](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/docs-get.html) 文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/get-doc.html)).
