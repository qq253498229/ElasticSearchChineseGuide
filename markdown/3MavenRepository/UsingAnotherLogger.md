# 使用其它的日志

你也可以使用其它的日志代替log4j2，例如slf4j以及其实现：
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-to-slf4j</artifactId>
    <version>2.9.1</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.24</version>
</dependency>
```

[这个页面](https://www.slf4j.org/manual.html)列出了可以使用的实现，选择你喜欢的方式添加依赖即可。

例如，使用slf4j-simple做日志：

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.21</version>
</dependency>
```