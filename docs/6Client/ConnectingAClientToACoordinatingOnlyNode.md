# 将client连接到一个节点
开始在本地[连接到一个节点](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/modules-node.html#coordinating-only-node)，
然后在应用中创建一个 [TransportClient](https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/transport-client.html)吧。
这样这个节点就可以加载你需要的任何插件了(例如discovery插件)。