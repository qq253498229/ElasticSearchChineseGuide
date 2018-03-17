# Update接口

你可以创建一个 **UpdateRequest** 并发送给 **client**

```java
UpdateRequest updateRequest = new UpdateRequest();
updateRequest.index("index");
updateRequest.type("type");
updateRequest.id("1");
updateRequest.doc(jsonBuilder()
        .startObject()
            .field("gender", "male")
        .endObject());
client.update(updateRequest).get();
```

或者也可以使用 **prepareUpdate()** 方法

```java
client.prepareUpdate("ttl", "doc", "1")
        //这里是脚本。它也可以是存储中本地文件中，这种情况下，你需要使用ScriptService.ScriptType.FILE
        .setScript(new Script("ctx._source.gender = \"male\""  , ScriptService.ScriptType.INLINE, null, null))
        .get();

client.prepareUpdate("ttl", "doc", "1")
        .setDoc(jsonBuilder()               
            .startObject()
                .field("gender", "male")
            .endObject())
        .get();
```

注意，不能同时使用 **script** 和 **doc** 。

## 使用脚本更新文档

Update接口允许提供一个脚本来更新文档：

```java
UpdateRequest updateRequest = new UpdateRequest("ttl", "doc", "1")
        .script(new Script("ctx._source.gender = \"male\""));
client.update(updateRequest).get();
```

## 合并的方式更新文档

Update接口允许传入一部分文档，并将之合并到一个已存在的文档中(简单的递归合并，对象的内部合并，替换键值对和数组)，例：

```java
UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
        .doc(jsonBuilder()
            .startObject()
                .field("gender", "male")
            .endObject());
client.update(updateRequest).get();
```

## 插入更新(Upsert)

更新的时候也支持**upsert**。如果文档不存在，**upsert**的元素会被建立一个索引：

```java
IndexRequest indexRequest = new IndexRequest("index", "type", "1")
        .source(jsonBuilder()
            .startObject()
                .field("name", "Joe Smith")
                .field("gender", "male")
            .endObject());
UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
        .doc(jsonBuilder()
            .startObject()
                .field("gender", "male")
            .endObject())
        //如果文档不存在，indexRequest中的元素就会被建立索引
        .upsert(indexRequest);              
client.update(updateRequest).get();
```

如果 **index/type/1** 这个文档已经存在了，在这个操作之后我们会有一个这样的文档：

```json
{
    "name"  : "Joe Dalton",
    "gender": "male" //这个字段是通过update request添加的
}
```

如果 **index/type/1** 这个文档不存在，我们会得到一个心的文档：

```json
{
    "name" : "Joe Smith",
    "gender": "male"
}
```

