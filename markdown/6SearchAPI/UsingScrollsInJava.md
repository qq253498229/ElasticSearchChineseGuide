# 使用游标查询

首先请阅读[游标查询](https://www.elastic.co/guide/cn/elasticsearch/guide/cn/scroll.html)文档。

```java
import static org.elasticsearch.index.query.QueryBuilders.*;

QueryBuilder qb = termQuery("multi", "test");

SearchResponse scrollResp = client.prepareSearch(test)
        .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
        .setScroll(new TimeValue(60000))
        .setQuery(qb)
        .setSize(100).get(); //每次查询最多返回100条结果
//没有结果返回的时候就停止循环
do {
    for (SearchHit hit : scrollResp.getHits().getHits()) {
        //处理返回结果
    }

    scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
} while(scrollResp.getHits().getHits().length != 0); // 没有返回结果的时候就停止循环和游标
```