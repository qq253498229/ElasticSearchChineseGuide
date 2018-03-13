# 生成JSON文档

有下面几种方式生成一个JSON文档：

- 手动使用**byte[]**或**String**类型来拼接一个JSON
- 使用**Map**类型会自动转换成等效的JSON
- 使用第三方库(例如:[Jackson](http://wiki.fasterxml.com/JacksonHome))来把你的Bean转换成Json格式
- 使用内置的 **XContentFactory.jsonBuilder()**  工具

在内部，所有类型都会转换成 **byte[]** 类型。因此，如果你的对象已经是这种格式了，那么就直接使用它吧。
当然 **jsonBuilder** 是一个高度优化的工具，你可以使用它来直接构造 **byte[]**

## 手动拼接

如果你使用过json格式，那么这里对你来说没什么困难的，
唯一需要注意的是你需要通过[Date Format](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping-date-format.html)
对日期进行编码


```java
String json = "{" +
        "\"user\":\"kimchy\"," +
        "\"postDate\":\"2013-01-30\"," +
        "\"message\":\"trying out Elasticsearch\"" +
    "}";
```

## 使用Map

Map是一个键值对的集合，它可以表现为一个JSON结构：

```java
Map<String, Object> json = new HashMap<String, Object>();
json.put("user","kimchy");
json.put("postDate",new Date());
json.put("message","trying out Elasticsearch");
```

## 使用第三方库

你可以使用[Jackson](http://wiki.fasterxml.com/JacksonHome)将你的Bean序列化成JSON。
请将[Jackson Databind](http://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)加入你的项目中。
然后你就可以使用 **ObjectMapper** 来序列化你的Bean了：

```java
import com.fasterxml.jackson.databind.*;

// instance a json mapper
ObjectMapper mapper = new ObjectMapper(); // create once, reuse

// generate json
byte[] json = mapper.writeValueAsBytes(yourbeaninstance);
```

## 使用ES内置的工具

ES提供了一个内置的工具来帮助你生成JSON内容：

```java
import static org.elasticsearch.common.xcontent.XContentFactory.*;

XContentBuilder builder = jsonBuilder()
    .startObject()
        .field("user", "kimchy")
        .field("postDate", new Date())
        .field("message", "trying out Elasticsearch")
    .endObject()
```

这里需要注意，你也可以使用 **startArray(String)** / **endArray()** 方法代替 **startObject()** / **endObject()** 方法添加一个数组。
顺便说一下，**field** 可以接受很多对象类型。你可以直接传一个**number**，**date** 或者是其它 **XContentBuilder** 类型的对象。

如果你想查看生成的JSON内容，你可以使用**string()**方法：

```java
String json = builder.string();
```
