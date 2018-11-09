
<p align="center">
 <h1> <img width="45px" src="https://img-blog.csdnimg.cn/20181109170115221.jpg">
Meteor-zookeeper</h1>

</p>

<p align="center">
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/java-1.8-brightgreen.svg" alt="java">
  </a>
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-2.5.10-brightgreen.svg" alt="vue-elem">
  </a>
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-2.5.10-brightgreen.svg" alt="vue">
  </a>
  <a href="https://github.com/ElemeFE/element">
    <img src="https://img.shields.io/badge/element--ui-2.3.2-brightgreen.svg" alt="element-ui">
  </a>
  <a href="https://travis-ci.org/PanJiaChen/vue-element-admin" rel="nofollow">
    <img src="https://travis-ci.org/PanJiaChen/vue-element-admin.svg?branch=master" alt="Build Status">
  </a>
  <a href="https://github.com/18106960985/meteor-curator-admin/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/mashape/apistatus.svg" alt="license">
  </a>
</p>

简体中文 | [English](./README.en.md)

## 简介

- [Meteor-zookeeper-core](https://github.com/18106960985/meteor-zookeeper) 是一个对zookeeper操作的集成方案，其核心设计目的
  - 对内高内聚、无入侵, 采用fastjson序列化与反序列化,内置了数据本地缓存，实现节点监听
  - 对外开箱即用、易扩展、轻量级,提供丰富API方法，它可以帮助你快速的管理zookeeper。
- [Meteor-zookeeper-web](https://github.com/18106960985/meteor-zookeeper)不光是  [Meteor-zookeeper-core](https://github.com/18106960985/meteor-zookeeper)的一个小示例，它为 [Meteor-zookeeper-core](https://github.com/18106960985/meteor-curator)提供了可视化操作，给使用者更舒服的体验。
- [Meteor-zookeeper-admin](https://github.com/18106960985/meteor-zookeeper-admin) 前端采用[vue-element-ui](https://github.com/PanJiaChen/vue-element-admin)  这里就不做更多阐述，在需要自定义UI的情况下请自行查看该项目说明

- [在线访问](http://111.230.210.81:7788/)
- [使用文档](https://blog.csdn.net/qq_16882073/article/details/83622399) 
- [Github](https://github.com/18106960985/meteor-zookeeper) 
  - [Meteor-zookeeper](https://github.com/18106960985/meteor-zookeeper) 
  - [Meteor-zookeeper-admin](https://github.com/18106960985/meteor-zookeeper-admin)
- [Gitee](https://gitee.com/y747718944/meteor-curator)
  - [Meteor-zookeeper](https://gitee.com/y747718944/meteor-curator)
  - [Meteor-zookeeper-admin](https://gitee.com/y747718944/meteor-zookeeper-admin)


**本项目类型属于服务组件，请勿对外网开放**<br>
**本项目类型属于服务组件，请勿对外网开放**<br>
**本项目类型属于服务组件，请勿对外网开放**<br>


## 升级计划
1、使用说明及教程完善。
<br>
其他
<br>
   =。=目前该项目已经满足了公司的使用，所以具体需要升级什么还请有需要的私信我。

##功能说明

- Zookeeper-code
  - **服务连接**  支持单zookeeper或是集群
  - **数据存储数据转换** 目前支持 string,list,map,yml,Properties,bean转化
  - **本地缓存**  获取的数据将会缓存与本地 .zk目录下 每次初始化会清除该文件
  - **节点变化监听**  实时监听节点数据变动
  - **节点监听忽略**  能自定义忽略不想监听的节点
  - **根节点预选** 在实际开发中，不是很推荐使用/作为根节点
  - **服务配置**  目前服务配置支持使用Yml文件或者直接编辑CuratorConfig 里的变量。
- Zookeeper-admin
  - **增删改查** 支持对ZNode增删改查
  - **数据导入导出**  因为公司不同环境都部署了zookeeper所以该功能只是简单的，配置这种东西基本上不会经常变动所以没有添加数据
  - **zookeeper服务切换** 同上
  - **角色** 目前只有2个角色一个是admin 一个是Tourist，这些都是由前端实现的，超级简单的，（我才不会告诉你这是我开始怕开源搞烂我的腾讯云加的）



**如有问题请先看上述使用文档和文章，若不能满足，欢迎 issue 和 pr**

## 系统架构图

 <p align="center">
   <img width="900" src="https://img-blog.csdnimg.cn/20181101162041302.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70 ">
</p>
 <p align="center">
  <img width="900" src="https://img-blog.csdnimg.cn/20181101163654155.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70">
</p>
 <p align="center">
  <img width="900" src="https://img-blog.csdnimg.cn/20181101163853531.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70">
</p>


# 使用手册
## Maven依赖

```
<dependency>
    <groupId>meteor-zookeeper</groupId>
    <artifactId>zookeeper-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## meteor.yml配置

```
# zookeeper服务器地址  默认为:"localhost:2181"  可以多个使用","隔开 如: zk-1:2181,zk-2:2181,zk-3:2181
servers: localhost:2181
# 项目以哪个节点作为根节点 不推荐 "/"作为根节点。 默认节点为"/"
rootPath: /cloud
# 重连次数 默认 10次
connCount: 10
#重新连接的间隔时间 默认 5秒
connInterval: 5000
#seessiont 超时时间 默认 180000毫秒
sesstionTimeOut: 180000
#连接超时时间 默认 60000毫秒
connTimtOut: 60000
# 监听节点 类型为List  curator-admin项目推荐 监听"/"，其他项目请自行斟酌
listener:
          - /cloud
# 忽略监听节点 类型为List   
listenerIgnores:
          - /meteor
```

## 使用方式
可以引入meteor.yml  请放置在resource下 。或者
为 CuratorConfig.java内的静态变量赋值 如
```
 CuratorConfig.ZK_SERVERS = localhost:2181
 CuratorConfig.ROOT_PATH = "/"
 ………………
```

## Zookeeper-admin 打包部署
```
#使用maven打包
mvn clean install

#zookeeper-admin target 下将会有2个可选运行方式

#1、jar 包运行方式

java -jar zookeeper-admin.jar 

#进阶方式，一般来说服务会部署在linux主机上并且我们会扔掉日志输出 

nohub java -jar zookeeper-admin.jar &2>/dev/null &

#2、zip 可编译文件方式 

unzipt zookeeper-admin-yyyy-mm-dd-hh.zip

cd zookeeper-admin/

```


## 前端开发

```bash
# 克隆项目
git clone https://github.com/18106960985/meteor-zookeeper-admin.git

# 安装依赖
npm install

# 建议不要用 cnpm 安装 会有各种诡异的bug 可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

浏览器访问 http://localhost:9527

## 发布

```bash
# 构建测试环境
npm run build:sit

# 构建生产环境
npm run build:prod

#打包完成后将src平级的 meteor文件里的内容复制到 后端项目 zookeeper-admin 的 main/resources/下 依托spring boot内嵌的tomcat运行.
#这是为了开发分离，部署合并
```



## Browsers support

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| --------- | --------- | --------- | --------- |
| IE10, IE11, Edge| last 2 versions| last 2 versions| last 2 versions

## License

[MIT](https://github.com/PanJiaChen/vue-element-admin/blob/master/LICENSE)

Copyright (c) 2018-present Meteor
