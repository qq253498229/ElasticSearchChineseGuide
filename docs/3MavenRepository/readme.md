# Maven仓库

ES 已经托管在 Maven 中央仓库

例如：您可以在maven项目的pom.xml中加入如下依赖来使用ES：

```xml
<dependency>
  <groupId>org.elasticsearch</groupId>
  <artifactId>elasticsearch</artifactId>
  <version>5.6.8</version>
</dependency>
```

```xml
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>transport</artifactId>
  <version>5.6.8</version>
</dependency>
```

```xml
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>elasticsearch-rest-high-level-client</artifactId>
  <version>5.6.8</version>
</dependency>
```

- [Log4j 2 日志](./log4j2.md)
- [使用其它的日志](./UsingAnotherLogger.md)