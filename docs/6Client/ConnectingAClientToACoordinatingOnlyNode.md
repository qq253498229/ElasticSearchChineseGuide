# Connecting a Client to a Coordinating Only Node
You can start locally a [Coordinating Only Node](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/modules-node.html#coordinating-only-node) 
and then simply create a **[TransportClient](https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/transport-client.html)** 
in your application which connects to this Coordinating Only Node.

This way, the coordinating only node will be able to load whatever plugin you need (think about discovery plugins for example).