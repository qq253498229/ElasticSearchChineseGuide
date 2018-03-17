# 通过脚本修改接口

**updateByQuery** 最简单的用法是更新单个索引中的每个文档而不更改源文件。这种用法还可以获取新的属性或其它映射。

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("source_index").abortOnVersionConflict(false);
BulkByScrollResponse response = updateByQuery.get();
```

调用 **updateByQuery** 方法会优先获取索引的快照，然后使用内部版本索引所有的文档。

> 当文档正在建立索引时，如果快照版本发生了改变，那么这个文档的版本会发生冲突。

当版本匹配时， **updateByQuery** 方法会更新文档并增加版本号。

所有的修改和查询都失败的时候， **updateByQuery** 方法就会停止。
这些失败的原因可以从 **BulkByScrollResponse#getIndexingFailures** 中获得。
但是所有成功的更新操作保留并且不会回滚。当第一次发生失败而导致终止时，响应会包含批量请求生成的所有失败消息。

为了防止版本冲突而导致 **updateByQuery** 方法停止，请设置 **abortOnVersionConflict(false)**。
第一个例子就是这样做的，因为这个例子是想获取映射的修改，并且版本冲突意味着冲突文档在 **updateByQuery** 
的开始和尝试更新修改文档之间更新。这样很好，因为修改操作会获取更新的映射。

**UpdateByQueryRequestBuilder** 接口支持过滤更新的文档，并且可以限制更新的数量，还可以通过脚本更新文档：

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("source_index")
    .filter(QueryBuilders.termQuery("level", "awesome"))
    .size(1000)
    .script(new Script(ScriptType.INLINE, "ctx._source.awesome = 'absolutely'", "painless", Collections.emptyMap()));
BulkByScrollResponse response = updateByQuery.get();
```

**UpdateByQueryRequestBuilder** 还支持直接获取用于查询文档的语句。你可以用它来改变默认的滚动数量，或者修改匹配文档的请求。

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("source_index")
    .source().setSize(500);
BulkByScrollResponse response = updateByQuery.get();
```

您还可以将条数和顺序结合使用，来限制需要更新的文档：

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("source_index").size(100)
    .source().addSort("cat", SortOrder.DESC);
BulkByScrollResponse response = updateByQuery.get();
```

除了修改文档的 **_source** 字段之外，您还可以使用脚本来修改操作，类似修改[Update接口](UpdateAPI.md)。

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("source_index")
    .script(new Script(
        ScriptType.INLINE,
        "if (ctx._source.awesome == 'absolutely) {"
            + "  ctx.op='noop'"
            + "} else if (ctx._source.awesome == 'lame') {"
            + "  ctx.op='delete'"
            + "} else {"
            + "ctx._source.awesome = 'absolutely'}",
        "painless",
        Collections.emptyMap()));
BulkByScrollResponse response = updateByQuery.get();
```

与[Update接口](UpdateAPI.md)一样，您可以设置 **ctx.op** 来改变执行的操作。

- noop:如果脚本没有改变，可以设置 **ctx.op = "noop"** 。 **updateByQuery** 会从修改列表中将它忽略，并且请求体中的 **noop** 会增加。
- delete:如果脚本决定删除，可以设置 **ctx.op = "delete"** 。 请求体中的 **delete** 会有报告。

**ctx.op** 设置其它值会报错。 **ctx** 设置其它字段也会报错。

这个接口不允许移动接触到的文档，只允许修改它。它就是这么设计的！我们没有规定从原始位置将文档移除。

你也可以一次对多个索引和类型执行这些操作，类似于[Search接口](../6SearchAPI/readme.md)

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source("foo", "bar").source().setTypes("a", "b");
BulkByScrollResponse response = updateByQuery.get();
```

如果你提供路由，那么该进程会将该路由的值复制到滚动查询中，从而将进程限制为匹配该路由值的分片：

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.source().setRouting("cat");
BulkByScrollResponse response = updateByQuery.get();
```

**updateByQuery** 也可以通过指定一个想这样的 **pipeline** 来使用 **ingest** 节点：

```java
UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
updateByQuery.setPipeline("hurray");
BulkByScrollResponse response = updateByQuery.get();
```

## 用于任务接口

你可以使用任务接口来获取所有正在运行的 **updateByQuery** 请求的状态：

```java
ListTasksResponse tasksList = client.admin().cluster().prepareListTasks()
    .setActions(UpdateByQueryAction.NAME).setDetailed(true).get();
for (TaskInfo info: tasksList.getTasks()) {
    TaskId taskId = info.getTaskId();
    BulkByScrollTask.Status status = (BulkByScrollTask.Status) info.getStatus();
    // do stuff
}
```

使用上面显示的 **TaskId** ，您可以直接查找任务：

```java
GetTaskResponse get = client.admin().cluster().prepareGetTask(taskId).get();
```

## 用于取消任务接口

所有 **updateByQuery** 可以使用任务取消接口：

```java
// Cancel all update-by-query requests
client.admin().cluster().prepareCancelTasks().setActions(UpdateByQueryAction.NAME).get().getTasks();
// Cancel a specific update-by-query request
client.admin().cluster().prepareCancelTasks().setTaskId(taskId).get().getTasks();
```

使用 **任务列表** 接口可以查询 **taskId** 的值。

取消请求通常非常快，但也需要几秒钟。任务状态接口会继续列出任务，直到取消完成。

## 重新节流

使用 **_rethrottle** 接口修改正在运行修改的 **requests_per_second** 值：

```java
RethrottleAction.INSTANCE.newRequestBuilder(client)
    .setTaskId(taskId)
    .setRequestsPerSecond(2.0f)
    .get();
```

使用 **任务列表** 接口可以查询 **taskId** 的值。

与 **updateByQuery** 一样，**requests_per_second** 可以设置任何正浮点值或 **Float.POSITIVE_INFINITY** 来设置节流级别。
加速进程的 **requests_per_second** 值立刻生效。要在完成当前批处理后减慢进程，可以设置 **requests_per_second** 来防止滚动。