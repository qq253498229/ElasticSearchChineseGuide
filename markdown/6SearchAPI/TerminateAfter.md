# 设置终止标志

设置一次查询的最大数，到达这个数量的时候，查询操作就会提前终止。
如果设置了的话，可以使用 **isTerminatedEarly()** 查看操作是否提前结束。例：

```java
SearchResponse sr = client.prepareSearch(INDEX)
    //查询到1000个结果就停止
    .setTerminateAfter(1000)    
    .get();

if (sr.isTerminatedEarly()) {
    // We finished early
}
```