# 管理索引接口

要想访问索引管理JavaAPI，你需要从[AdminClient](readme.md)类中调用 **indices()** 方法。

```java
IndicesAdminClient indicesAdminClient = client.admin().indices();
```

- [创建索引](#创建索引)
- [设置映射](#设置映射)
- [显式刷新](#显式刷新)
- [获取映射](#获取设置)
- [更新索引映射](#更新索引设置)

## 创建索引

[管理索引接口](#管理索引接口)可以通过默认设置和无设置来创建索引。

```java
client.admin().indices().prepareCreate("twitter").get();
```

### 索引设置

创建的每个索引都会具有指定的设置：

```java
client.admin().indices().prepareCreate("twitter")
        .setSettings(Settings.builder()             
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        )
        //执行并等待结果的返回
        .get();  
```

## 设置映射

设置映射接口可以在创建索引的时候添加一个新的type：

```java
client.admin().indices()
        //创建一个名字叫twitter的索引
        .prepareCreate("twitter")   
        //同时会增加一个名为tweet的type
        .addMapping("\"tweet\": {\n" +              
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}")
        .get();
```

设置映射接口还允许想现有的索引中添加新字段：

```java
client.admin().indices()
//修改已经存在的名为twitter的索引
.preparePutMapping("twitter")   
//创建一个名为user的type
.setType("user")     
//user具有预定义的类型
.setSource("{\n" +                                      
        "  \"properties\": {\n" +
        "    \"name\": {\n" +
        "      \"type\": \"text\"\n" +
        "    }\n" +
        "  }\n" +
        "}", XContentType.JSON)
.get();

// You can also provide the type in the source document
client.admin().indices().preparePutMapping("twitter")
.setType("user")
.setSource("{\n" +
             //type也可以在source中指定
        "    \"user\":{\n" +                            
        "        \"properties\": {\n" +
        "            \"name\": {\n" +
        "                \"type\": \"text\"\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "}", XContentType.JSON)
.get();
```

也可以修改已经存在的映射：

```java
client.admin().indices().preparePutMapping("twitter")   
.setType("user")                                        
.setSource("{\n" +                                      
        "  \"properties\": {\n" +
        //新字段user_name
        "    \"user_name\": {\n" +
        "      \"type\": \"text\"\n" +
        "    }\n" +
        "  }\n" +
        "}", XContentType.JSON)
.get();
```

## 显式刷新

刷新接口允许显式地刷新一个或多个索引：

```java
//刷新所有索引
client.admin().indices().prepareRefresh().get(); 
//刷新一个索引
client.admin().indices()
        .prepareRefresh("twitter")               
        .get();
//刷新多个索引
client.admin().indices()
        .prepareRefresh("twitter", "company")   
        .get();
```


## 获取设置

获取映射接口允许查询索引的设置：

```java
GetSettingsResponse response = client.admin().indices()
        //获取company和employee的设置
        .prepareGetSettings("company", "employee").get();                           
//遍历结果
for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) { 
    //索引名
    String index = cursor.key;
    //索引的设置
    Settings settings = cursor.value;                                               
    //索引的分片数
    Integer shards = settings.getAsInt("index.number_of_shards", null);             
    //索引的副本数
    Integer replicas = settings.getAsInt("index.number_of_replicas", null);         
}
```

## 更新索引设置

你可以通过下面的方式来改变索引的设置：

```java
client.admin().indices()
        //需要改变的索引名
        .prepareUpdateSettings("twitter")   
        //需要改变的设置
        .setSettings(Settings.builder()                     
                .put("index.number_of_replicas", 0)
        )
        .get();
```
