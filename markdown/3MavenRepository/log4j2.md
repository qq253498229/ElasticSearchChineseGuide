# Log4j 2 日志

###### 加入log4j2来监控日志：
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.9.1</version>
</dependency>
```

###### 加入log4j2的配置文件，例如在src/main/resource中加入log4j2.properties文件，示例内容如下：
```properties
appender.console.type = Console
appender.console.name = console
appender.console.layout.type = PatternLayout

rootLogger.level = info
rootLogger.appenderRef.console.ref = console
```