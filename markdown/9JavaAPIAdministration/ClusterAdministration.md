# 管理集群接口

想要访问集群接口，你需要从[AdminClient](readme.md#管理员接口)类中调用 **cluster()** 。

```java
ClusterAdminClient clusterAdminClient = client.admin().cluster();
```

- [集群健康接口](#集群健康接口)
- [存储脚本接口](#存储脚本接口)

## 集群健康接口

### 健康

集群健康接口允许将集群的健康状态返回一个简单的状态，并且还可以为您提供每个索引的集群状态的技术信息：

```java
//获取所有索引的信息
ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get(); 
//集群名
String clusterName = healths.getClusterName();              
//获取数据节点总数
int numberOfDataNodes = healths.getNumberOfDataNodes();     
//获取节点总数
int numberOfNodes = healths.getNumberOfNodes();             

//遍历所有索引    
for (ClusterIndexHealth health : healths.getIndices().values()) { 
    //索引名
    String index = health.getIndex();                       
    //分片数
    int numberOfShards = health.getNumberOfShards();        
    //备份数
    int numberOfReplicas = health.getNumberOfReplicas();    
    //状态码
    ClusterHealthStatus status = health.getStatus();        
}
```

### 等待状态

可以使用集群状态接口等待整个集群或给定索引的特定状态：

```java
//准备请求
client.admin().cluster().prepareHealth() 
        //等待集群变为黄色           
        .setWaitForYellowStatus()                   
        .get();
//为company索引准备请求
client.admin().cluster().prepareHealth("company")   
        //等待集群变为绿色
        .setWaitForGreenStatus()                    
        .get();

client.admin().cluster().prepareHealth("employee")  
        .setWaitForGreenStatus()
        //最多等待2秒钟                    
        .setTimeout(TimeValue.timeValueSeconds(2))  
        .get();
```

如果索引不具备预期状态，并且你希望在这种情况下失败，必须明确指定：

```java
ClusterHealthResponse response = client.admin().cluster().prepareHealth("company")
        //等待变为绿色
        .setWaitForGreenStatus()    
        .get();

ClusterHealthStatus status = response.getIndices().get("company").getStatus();
if (!status.equals(ClusterHealthStatus.GREEN)) {
    //如果不是绿色则抛出异常
    throw new RuntimeException("Index is in " + status + " state"); 
}
```

## 存储脚本接口

存储脚本接口允许用户在脚本和模版中间进行交互。也可以用来创建、更新和删除存储的脚本和模版：

```java
PutStoredScriptResponse response = client.admin().cluster().preparePutStoredScript()
                .setId("script1")
                .setContent(new BytesArray("{\"script\": {\"lang\": \"painless\", \"source\": \"_score * doc['my_numeric_field'].value\"} }"), XContentType.JSON)
                .get();

GetStoredScriptResponse response = client().admin().cluster().prepareGetStoredScript()
                .setId("script1")
                .get();

DeleteStoredScriptResponse response = client().admin().cluster().prepareDeleteStoredScript()
                .setId("script1")
                .get();
```

也可以简单的使用 **mustache** 作为 **scriptLang** 来存储脚本

### 脚本语言

存储脚本接口允许你设置存储脚本的语言。如果没有提供的话，则使用默认的脚本语言。