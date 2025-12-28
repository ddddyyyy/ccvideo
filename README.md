# CC视频的API接口封装

## 配置方式

引入以下pom坐标

```xml
<dependency>
    <groupId>io.github.ddddyyyy</groupId>
    <artifactId>ccvideo-spring-boot-starter</artifactId>
    <version>1.2</version>
</dependency>
```

### SpringBoot

application.yaml进行以下配置

```yaml
ccvideo:
  # 是否启用
  switch: true
  # CC视频点播的key
  key: xxx
  # CC视频直播的key
  roomKey: xxx
  # 用户ID
  userid: xxx
```

### 普通Java工程

JVM启动参数添加以下参数

```shell
-Dccvideo.switch=true -Dccvideo.key=CC视频点播的key -Dccvideo.roomKey=CC视频直播的key -Dccvideo.userid=用户ID
```

## 使用方式

THQSUtil封装里各种API，直接使用即可，举例如下

```java
  // 获取视频信息
  THQSUtil util = new THQSUtil();
  JSONObject object = JSON.parseObject(util.getVideoInfo("xxx", null));
```
