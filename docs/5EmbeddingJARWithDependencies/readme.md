# 嵌入依赖的JAR

如果你想创建一个单独的jar包，包含你的应用和所有的依赖，你最好不要使用 *maven-assembly-plugin* 插件。
因为它无法处理Lucene所使用的*META-INF/services*这样的结构。

但是，你可以使用*maven-shade-plugin*，如下配置：
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>2.4.1</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals><goal>shade</goal></goals>
      <configuration>
        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
        </transformers>
      </configuration>
    </execution>
  </executions>
</plugin>
```
>请注意，如果你想在运行*java -jar yourjar.jar*的时候自动调用*main class*，你可以在*transformers*中按照如下方式添加：
```xml
<transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
  <mainClass>org.elasticsearch.demo.Generate</mainClass>
</transformer>
```