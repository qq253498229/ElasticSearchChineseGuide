# Client

你有很多方法来使用 **java client**

- 在一个已有的集群中执行标准的[index]()，[get]()，[delete]()和[search]()操作。
- 在一个已有的集群中执行管理任务

获取 ES **client** 很简单。最常用的方法就是创建一个连接到集群的[TransportClient]()，并从中获取client。

> 重要：集群中的客户端节点必须重要版本相同（例如5.6.8和5.6.2，2.4.6和2.4.0）。
虽然不同版本的客户端可以同时连接到同一个集群中，但是一些新版本的新特性在旧版本可能就不会被支持了。
所以理想情况就是客户端和集群中的节点版本都相同

> 警告：在ES7.0中我们不建议您使用TransportClient，它将会在8.0中被移除。
所以请尽量快速地习惯并使用JavaHighLevelClient。JavaHighLevelClient目前已经支持大部分常用的API，但是还有一些需要补充。
你可以点击[这里](https://github.com/elastic/elasticsearch/issues/27205)把你的应用需要添加的api告诉我们。
任何尚未支持的API仍然可以使用 [low level Java REST Client]() 通过json的方式来调用。

- [Transport Client](TransportClient.md)
- [将client连接到一个节点](ConnectingAClientToACoordinatingOnlyNode.md)