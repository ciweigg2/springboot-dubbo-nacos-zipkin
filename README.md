# springboot-dubbo-nacos

springboot-dubbo集成nacos注册中心和配置中心和zipkin链路追踪demo

最新dubbo-spring-boot-starter包在:https://oss.sonatype.org/content/repositories/snapshots

![调试信息](/images/1.png)

![链路信息](/images/2.png)

## 启动需要添加的参数

> 需要启动sentinel和每个服务添加启动参数

```java
java -Dserver.port=8089 -Dcsp.sentinel.dashboard.server=localhost:8089 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.4.1.jar
```

> 服务：

```java
-Djava.net.preferIPv4Stack=true -Dcsp.sentinel.api.port=8721 -Dcsp.sentinel.dashboard.server=localhost:8089 -Dproject.name=dubbo-consumer

-Djava.net.preferIPv4Stack=true -Dcsp.sentinel.api.port=8721 -Dcsp.sentinel.dashboard.server=localhost:8089 -Dproject.name=user-provider2 

-Djava.net.preferIPv4Stack=true -Dcsp.sentinel.api.port=8720 -Dcsp.sentinel.dashboard.server=localhost:8089 -Dproject.name=demo-provider1 
```