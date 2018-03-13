# Get接口

Get接口允许根据id从index中获取JSON格式的document。
下面的例子就是从index是twitter的索引中获取一个JSON格式的document，它的type是tweet，id是1：

```java
GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
```
有关Get操作的更多信息，请查阅REST [get](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/docs-get.html) 文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/get-doc.html)).

## 操作线程

Get接口允许一个实际的接口被调用的时候设置线程模型(接口执行在单台服务器的分片上)。

这些选项是为了在不同的线程上执行操作，或者在调用线程的时候执行它（注意接口仍然是异步的）。 
默认情况下，**operationThreaded** 被设置为true，这意味着该操作在不同的线程上执行。
这个例子里我们将它设置为**false**：
```java
GetResponse response = client.prepareGet("twitter", "tweet", "1")
        .setOperationThreaded(false)
        .get();
```