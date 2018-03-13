# Delete By Query接口

Delete By Query接口可以根据查询的结果删除一组文档：

```java
BulkByScrollResponse response =
    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
        //query
        .filter(QueryBuilders.matchQuery("gender", "male")) 
        //index
        .source("persons")                                  
        //执行操作
        .get();                                             
//返回删除了多少个文档
long deleted = response.getDeleted();     
```

由于这个操作可能会运行很长时间，如果你希望异步执行，可以使用**execute**代替**get**来执行，并提供一个**listener**，如下：

```java
DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    //query
    .filter(QueryBuilders.matchQuery("gender", "male"))                  
    //index
    .source("persons")               
    //listener                                    
    .execute(new ActionListener<BulkByScrollResponse>() {           
        @Override
        public void onResponse(BulkByScrollResponse response) {
            //返回删除了多少个文档
            long deleted = response.getDeleted();                        
        }
        @Override
        public void onFailure(Exception e) {
            // 处理异常
        }
    });
```