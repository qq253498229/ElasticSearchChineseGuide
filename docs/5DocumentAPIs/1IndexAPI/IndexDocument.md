# index文档

下面的例子的作用是，将一个JSON在ES中建立索引，index为twitter，type是tweet，id是1。
其中有三个属性，user的值是kimchy，postDate的属性是当前时间，message的属性是trying out Elasticsearch：

```java
import static org.elasticsearch.common.xcontent.XContentFactory.*;

IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
        .setSource(jsonBuilder()
                    .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                    .endObject()
                  )
        .get();
```

注意，你也可以在建立索引的时候不指定ID，这样ES会给你生成一个ID：

```java
String json = "{" +
        "\"user\":\"kimchy\"," +
        "\"postDate\":\"2013-01-30\"," +
        "\"message\":\"trying out Elasticsearch\"" +
    "}";

IndexResponse response = client.prepareIndex("twitter", "tweet")
        .setSource(json, XContentType.JSON)
        .get();
```

结果可以在**IndexResponse**中查看

```java
// Index name
String _index = response.getIndex();
// Type name
String _type = response.getType();
// Document ID (generated or not)
String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
long _version = response.getVersion();
// status has stored current instance statement.
RestStatus status = response.status();
```

有关索引操作的更多信息，请查阅REST [index](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/docs-index_.html)文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/index-doc.html))。