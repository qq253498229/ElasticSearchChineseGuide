# Transport Client

**TransportClient** 使用 transport模块远程连接到ES集群。
它并不是加入到集群中，而只是获取一个或多个初始传输地址，在操作的时候以循环的方式与之通信(大部分操作可能都是TwoHop操作)

```java
// on startup

TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"), 9300))
        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

// on shutdown

client.close();
```

注意如果你的集群名称不是 **elasticsearch** 的话，你需要设置成你自己的：

```java
Settings settings = Settings.builder()
        .put("cluster.name", "myClusterName").build();
TransportClient client = new PreBuiltTransportClient(settings);
//Add transport addresses and do something with the client...
```

**Transport client** 具有嗅探的特性，可以自动添加新主机和删除旧主机。
当嗅探被开启时，**transport client**通过调用**addTransportAddress**方法与内部节点列表中的某一个节点建立连接。
在这之后，**client**就可以调用节点内部的API来获取数据了。
客户端内部的节点列表会替换这些数据节点，列表默认情况下每5秒刷新一次。
注意嗅探所连接的ip地址可以声明在es的配置文件中。

请记住，如果该节点连接的不是数据节点，那么这个节点列表可能不会包含它的原始节点。
例如，你初始化并连接到了一个主节点，在嗅探之后，接下来的请求都不会发送到这个主节点，而是发送到其它的数据节点。
transport client排除非数据节点的原因，是为了避免向主节点发送搜索请求。

想起用嗅探特性，请设置**client.transport.sniff**为**true**：

```java
Settings settings = Settings.builder()
        .put("client.transport.sniff", true).build();
TransportClient client = new PreBuiltTransportClient(settings);
```

transport client级别的其它设置：

参数 | 作用
---|---
client.transport.ignore_cluster_name | 设置成**true**可以忽略节点名称的校验。(v0.19.4+)
client.transport.ping_timeout | ping超时时间，默认**5**秒。
client.transport.nodes_sampler_interval | sample/ping节点列表并连接的间隔，默认**5**秒