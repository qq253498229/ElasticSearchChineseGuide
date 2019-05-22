# Lucene Snapshot仓库

有时候主要版本(如测试版)的第一个版本可能建立在Lucene的快照版本之上，这时候您可能无法解决Lucene的依赖问题。

例如，如果您使用的是ES6.0.0-beta1版本，那么它依赖的是7.0.0-snapshot-00142c9版本的Lucene，这时候就需要定义以下仓库：

```xml
<repository>
    <id>elastic-lucene-snapshots</id>
    <name>Elastic Lucene Snapshots</name>
    <url>http://s3.amazonaws.com/download.elasticsearch.org/lucenesnapshots/00142c9</url>
    <releases><enabled>true</enabled></releases>
    <snapshots><enabled>false</enabled></snapshots>
</repository>
```

gradle:

```properties
maven {
    url 'http://s3.amazonaws.com/download.elasticsearch.org/lucenesnapshots/00142c9'
}
```