# 模版查询

查看[Search Template](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/search-template.html)文档。

将模版参数定义成 **Map<String,Object>** 类型：

```java
Map<String, Object> template_params = new HashMap<>();
template_params.put("param_gender", "male");
```

可以将模版存储在 **config/scripts** 目录下。如果你的脚本在 **config/scripts/template_gender.mustache** ，则可以这样查询：

```java
{
    "query" : {
        "match" : {
            "gender" : "{{param_gender}}"
        }
    }
}
```

创建你的模版查询请求：

```java
SearchResponse sr = new SearchTemplateRequestBuilder(client)
    //模版名
    .setScript("template_gender")       
    //模版存储的位置是本地文件
    .setScriptType(ScriptService.ScriptType.FILE) 
    //模版参数
    .setScriptParams(template_params)   
    //设置请求(可以在这里定义index)          
    .setRequest(new SearchRequest()) 
    //执行并返回查询响应             
    .get()               
    //从响应中获取查询结果                         
    .getResponse();  
```

你也可以将模版存储在集群中：

```java
client.admin().cluster().preparePutStoredScript()
    .setScriptLang("mustache")
    .setId("template_gender")
    .setSource(new BytesArray(
        "{\n" +
        "    \"query\" : {\n" +
        "        \"match\" : {\n" +
        "            \"gender\" : \"{{param_gender}}\"\n" +
        "        }\n" +
        "    }\n" +
        "}")).get();
```

要执行存储在集群中的模版，需要使用 **ScriptService.ScriptType.STORED** 类型：

```java
SearchResponse sr = new SearchTemplateRequestBuilder(client)
        .setScript("template_gender")     
        //模版存储的位置是集群                  
        .setScriptType(ScriptType.STORED)     
        .setScriptParams(template_params)                   
        .setRequest(new SearchRequest())                    
        .get()                                              
        .getResponse();   
```

也可以使用内联模版：

```java
sr = new SearchTemplateRequestBuilder(client)
        .setScript("{\n" +                                  
                "        \"query\" : {\n" +
                "            \"match\" : {\n" +
                "                \"gender\" : \"{{param_gender}}\"\n" +
                "            }\n" +
                "        }\n" +
                "}")
        //设置模版类型是内联模版
        .setScriptType(ScriptType.INLINE)    
        .setScriptParams(template_params)                  
        .setRequest(new SearchRequest())                   
        .get()                                             
        .getResponse();   
```