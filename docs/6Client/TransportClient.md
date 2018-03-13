# Transport Client

The **TransportClient** connects remotely to an Elasticsearch cluster using the transport module. 
It does not join the cluster, but simply gets one or more initial transport addresses and communicates with them in round robin fashion on each action 
(though most actions will probably be "two hop" operations).

```java
// on startup

TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"), 9300))
        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

// on shutdown

client.close();
```

Note that you have to set the cluster name if you use one different than "elasticsearch":

```java
Settings settings = Settings.builder()
        .put("cluster.name", "myClusterName").build();
TransportClient client = new PreBuiltTransportClient(settings);
//Add transport addresses and do something with the client...
```

The Transport client comes with a cluster sniffing feature which allows it to dynamically add new hosts and remove old ones. 
When sniffing is enabled, the transport client will connect to the nodes in its internal node list, which is built via calls to **addTransportAddress**. 
After this, the client will call the internal cluster state API on those nodes to discover available data nodes. 
The internal node list of the client will be replaced with those data nodes only. This list is refreshed every five seconds by default. 
Note that the IP addresses the sniffer connects to are the ones declared as the publish address in those node’s elasticsearch config.

Keep in mind that the list might possibly not include the original node it connected to if that node is not a data node. 
If, for instance, you initially connect to a master node, after sniffing, no further requests will go to that master node, 
but rather to any data nodes instead. The reason the transport client excludes non-data nodes is to avoid sending search traffic to master only nodes.

In order to enable sniffing, set **client.transport.sniff** to true:

```java
Settings settings = Settings.builder()
        .put("client.transport.sniff", true).build();
TransportClient client = new PreBuiltTransportClient(settings);
```

Other transport client level settings include:

参数 | 作用
---|---
client.transport.ignore_cluster_name | Set to **true** to ignore cluster name validation of connected nodes. (since 0.19.4)
client.transport.ping_timeout | The time to wait for a ping response from a node. Defaults to **5s** .
client.transport.nodes_sampler_interval | How often to sample / ping the nodes listed and connected. Defaults to **5s** .