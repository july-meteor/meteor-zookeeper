
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

 [简体中文](README.zh.md) | English

## Introduction

- [Meteor-zookeeper-core](https://github.com/18106960985/meteor-zookeeper)is a zookeeper management background . It based on apache-curator .
- [Meteor-zookeeper-web](https://github.com/18106960985/meteor-zookeeper) admin zookeeper by  [Meteor-zookeeper-core](https://github.com/18106960985/meteor-zookeeper)
- [Meteor-zookeeper-admin](https://github.com/18106960985/meteor-zookeeper-admin) 

- [Preview](http://111.230.210.81:7788/)
- [Documentation](https://blog.csdn.net/qq_16882073/article/details/83622399) 
- [Github](https://github.com/18106960985/meteor-zookeeper) 
  - [Meteor-zookeeper](https://github.com/18106960985/meteor-zookeeper) 
  - [Meteor-zookeeper-admin](https://github.com/18106960985/meteor-zookeeper-admin)
- [Gitee](https://gitee.com/y747718944/meteor-curator) 码云地址
  - [Meteor-zookeeper](https://gitee.com/y747718944/meteor-curator)
  - [Meteor-zookeeper-admin](https://gitee.com/y747718944/meteor-curator-admin)




##Function Description

- Zookeeper-code
  - **Service connection**  Connection to zookeeper servers
  - **Data conversion** support string、list、map、yml、Properties and bean conversion
  - **Local cache**  Cache server data with local
  - **Node change listener**  Real-time monitoring of node data changes
  - **Node listener ignore**  Can customize to ignore nodes that you don't want to listen to
  - **Root setting** 
  - **Service configuration**  use yml config or modify CuratorConfig
- Zookeeper-admin
  - **crud**  crud to ZNode
  - **Import And Export **  
  - **Zookeeper servers switch** 同上
  - **roles**  Admin and Tourist




## System architecture diagram

 <p align="center">
   <img width="900" src="https://img-blog.csdnimg.cn/20181101162041302.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70 ">
</p>
 <p align="center">
  <img width="900" src="https://img-blog.csdnimg.cn/20181101163654155.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70">
</p>
 <p align="center">
  <img width="900" src="https://img-blog.csdnimg.cn/20181101163853531.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE2ODgyMDcz,size_16,color_FFFFFF,t_70">
</p>


# Use manual
## Maven

```
<dependency>
    <groupId>meteor-zookeeper</groupId>
    <artifactId>zookeeper-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## meteor.yml Configuration

Please put under the main/resource/
```
# zookeeper servers defalut to localhost:2181   Can be separated by multiple "," AS  zk-1:2181,zk-2:2181,zk-3:2181
servers: localhost:2181

# znode root path
rootPath: /cloud

# Number of reconnections defalut to 10
connCount: 10

#Reconnection interval defalut to 5
connInterval: 5000

sesstionTimeOut: 180000
#connections out  defalut to 60000
connTimtOut: 60000

# node listener  type is list 
listener:
          - /cloud
# node not listener  type is list   
listenerIgnores:
          - /meteor
```

## Way of use
use meteor.yml or 
```
 CuratorConfig.ZK_SERVERS = localhost:2181
 CuratorConfig.ROOT_PATH = "/"
 ………………
```

## Zookeeper-admin  Build
```
# use maven
mvn clean install

#zookeeper-admin target 

# jar run

java -jar zookeeper-admin.jar 

#Advance

nohub java -jar zookeeper-admin.jar &2>/dev/null &

#zip run 

unzipt zookeeper-admin-yyyy-mm-dd-hh.zip

cd zookeeper-admin/

```


## Getting started

```bash
# clone the project
git clone https://github.com/18106960985/meteor-zookeeper-admin.git

# install dependency
npm install



# develop
npm run dev
```

This will automatically open http://localhost:9527.

## Build


```bash
# build for test environment
npm run build:sit

# build for production environment
npm run build:prod

mv meteor/ /xxxx/xx/meteor-
```
另介绍一个有意思的项目,关于足球赔率数据爬虫分析一体化的项目 https://github.com/monomania/foot


## Browsers support

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| --------- | --------- | --------- | --------- |
| IE10, IE11, Edge| last 2 versions| last 2 versions| last 2 versions

## License

[MIT](https://github.com/PanJiaChen/vue-element-admin/blob/master/LICENSE)

Copyright (c) 2018-present Meteor
