# 使用Bulk Processor

**Bulk Processor** 类提供了一个简单的接口来自动执行批处理操作，它可以设置的规则有：请求数，请求数据大小，周期。

要使用它，首先要创建一个**BulkProcessor**实例：

```java
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

BulkProcessor bulkProcessor = BulkProcessor.builder(
        //添加ES client
        client,  
        new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId,
                                   BulkRequest request) { 
                                      //在批处理之前执行，其中request.numberOfActions()方法可以获取批处理的数量
                                      ... 
                                   } 

            @Override
            public void afterBulk(long executionId,
                                  BulkRequest request,
                                  BulkResponse response) { 
                                      //在批处理之后执行，其中response.hasFailures()方法可以查看失败的请求
                                      ... 
                                   }

            @Override
            public void afterBulk(long executionId,
                                  BulkRequest request,
                                  Throwable failure) {  
                                      //在批处理执行失败，或异常的时候执行
                                      ... 
                                   }
        })
        //到达10000个请求就立刻执行批处理
        .setBulkActions(10000) 
        //到达5MB的大小就立刻执行批处理
        .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) 
        //每隔5秒就立刻执行批处理
        .setFlushInterval(TimeValue.timeValueSeconds(5)) 
        //设置执行批处理请求时允许的最大并发数。0代表每次只执行一个请求，1代表允许1条并发请求
        .setConcurrentRequests(1) 
        //设置异常策略，间隔100ms重试3次。当批处理执行的时候抛出EsRejectedExecutionException异常的时候，会启动重试策略。原因可能是硬件资源太低。如想关闭该策略，可以传入BackoffPolicy.noBackoff()
        .setBackoffPolicy(
            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)) 
        .build();
```

默认情况，BulkProcessor的配置如下：

- 1000个请求
- 5mb大小
- 没有间隔
- 允许1个并发请求
- 每50ms重试一次，一共重试8次，总时间大概5.1秒左右


## 添加请求

现在可以向**Bulk Processor**中添加请求了

```java
bulkProcessor.add(new IndexRequest("twitter", "tweet", "1").source(/* your doc here */));
bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));
```

## 关闭Bulk Processor

当确认所有的文档全部发送到**BulkProcessor**之后，可以使用**awaitClose**或**close**方法关闭它。

```java
bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
```

或者

```java
bulkProcessor.close();
```

如果设置了**flushInterval**，上面两个方法都可以发送剩余的文档并且禁用发送任务。
如果开启了并发请求策略，**awaitClose**方法会等待所指定的时间，当所有批处理请求全部完成时，会返回**true**，
如果在指定时间内，仍有批处理请求未完成，则会返回**false**。
**close**方法不会等待批处理请求是否完成，而是直接取消剩余的所有请求。


## 测试Bulk Processor

如果你正在测试ES并且使用的是**BulkProcessor**来批量添加数据，最好将并发请求数量设置为**0**，这样批处理请求将会以同步的方式执行：

```java
BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() { /* Listener methods */ })
        .setBulkActions(10000)
        .setConcurrentRequests(0)
        .build();

// 添加请求
bulkProcessor.add(/* Your requests */);

// 发送所有剩余请求
bulkProcessor.flush();

// 在不需要的时候关闭bulkProcessor
bulkProcessor.close();

// 刷新indices
client.admin().indices().prepareRefresh().get();

// 现在可以开始检索了!
client.prepareSearch().get();
```