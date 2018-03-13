# Multi Get接口

Multi Get接口允许通过index,type,id获取文档列表：

```json
MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
    //通过id获取
    .add("twitter", "tweet", "1")           
    //或者通过相同的index/type，和多个id
    .add("twitter", "tweet", "2", "3", "4") 
    //也可以从其它的索引中获取文档
    .add("another", "type", "foo")          
    .get();
//遍历结果集
for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
    GetResponse response = itemResponse.getResponse();
    //查看文档是否存在
    if (response.isExists()) {
        //获取_source字段
        String json = response.getSourceAsString(); 
    }
}
```

有关Get操作的更多信息，请查阅 [multi get接口](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/docs-multi-get.html)
文档([中文](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_Retrieving_Multiple_Documents.html))。