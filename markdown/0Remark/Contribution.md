# 贡献说明

首先欢迎一切形式的贡献，例如文档补充、修改、建议意见等，途径包括但不限于issue/pull request/email。
以下说明只是更好的维护文档，让更多的人得到帮助，仅仅是提倡而不是强制。

- 使用markdown语法
- link文件名中间不要有空格，否则GitHub会不识别
- 个人使用心得请写在md文件末尾并作出备注(例如: 心得：xxxxxx)
- 为了对接elastic官方文档，之后会加入AscIIDoc语法，md版本不会停止更新。


## 目录结构


```
- asciidoc adoc文档目录
  - base 文档基础依赖
    - images 图片目录
    - css.css 生成文档样式文件
    - docinfo.html 文档toc生成工具，自己又加了一个版本select框
  - cn 中文翻译目录，最后用这个目录编译
    - other.adoc 补充文档
    - 6.8.0 版本号
    - 7.1.0 ...
  - en 官方文档拷贝目录
- docs 生成html目录，GithubPage使用
  - index.html 仅用作跳转使用，目前默认跳转到6.8.0版本
  - images 图片目录
  - 6.8.0 生成目录
    - index.html 生成的文档html，本地可以直接打开
  - 7.1.0 ...
- es docker镜像目录，目前还是6.2.2版本，没有更新
- example es shell请求测试目录
- java-es-test es java请求测试目录
- markdown 6.2.2版本文档目录
```