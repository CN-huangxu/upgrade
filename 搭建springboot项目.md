# 搭建 Springboot 项目

## 1. [项目初始化](https://www.jianshu.com/p/a55b8675f5ca)

    1. 打开idea 创建项目路径：File-New-NewProject。 next 进入参数设置页面
    2. 设置参数。 next 进入功能选择
    3. 选择需要的功能  web  Spring web。 next 输入项目名字
    4. 项目 存储位置 finish

> 注意
>
> 1.  服务 URL 选 https://start.springboot.io
> 2.  首次启动 可能需要设置
>     项目结构/ 项目 / 项目 sdk
>     项目结构/ 模块 : 标记 src 为 源

## 2. [集成 mybatis](https://zhuanlan.zhihu.com/p/160901686)

1. 创建数据库 / 建数据库表
2. 配置数据库连接池 集成 Mybatis

添加依赖

```xml
<!--数据驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
<!--数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.9</version>
        </dependency>
<!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
```

配置数据源和 mybatis 配置()

```properties
#	mapper xml 文件地址
mybatis.mapper-locations=classpath*:mapper/*Mapper.xml

#	数据库url
spring.datasource.url=jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=UTC
#	数据库用户名
spring.datasource.username=root
#	数据库密码
spring.datasource.password=root
#	数据库驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

> 注意
>
> 1.  用户名密码不要带空格
> 2.  数据库驱动不要配错了
>     MySQL5.x 的版本使用的驱动类是 com.mysql.jdbc.Driver
>     MySQL8.x 的版本使用的驱动类是 com.mysql.cj.jdbc.Driver
> 3.  MySQL 数据库默认使用的是美国的时区，而我们连接的时候用的是中国的北京时间，然后比美国晚上 8 个小时，所以当我们在连接数据库的时候要设置一下时区为东八区 ServerTimezone=UT

3. 在 pojo 包下创建 User 对象，并实现序列化接口 Serializable

```java
public	class	User implements	Serializable{
  private	Integer	id;         //	用户id
  private	String	username;    //用户名
  private	Integer	age;        //	年龄
  //	省略	setter、getter方法
}
```

> 什么是序列化？
>
> 序列化将数据分解成字节流，以便存储在文件中或在网络上传输。
> 反序列化就是打开字节流并重构对象。

4. 创建 UserMapper 接口和对应的 UserMapper.xml 文件

在 mapper 包下创建 UserMapper 接口，并在接口中定义各一个方法

并在 resource 包下创建依次创建 com/example/demo/mapper/UserMapper.xml

## 3. [集成 swagger](https://segmentfault.com/a/1190000037455077)

1. 引入依赖 springfox-boot-starter：

2. 自定义配置信息

3. 在你的 Controller 上添加 swagger 注解

4. 如启用了访问权限，还需将 swagger 相关 uri 允许匿名访问

5. 启动应用，访问/swagger-ui/index.html

6. swagger3.0 需要设置（application.properties）
   spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER

> 啥是 ANT_PATH_MATCHER
>
> (Ant 风格简单的讲，它是一种精简的匹配模式，仅用于匹配路径 or 目录。使用大家熟悉的（这点很关键）的通配符[https://cloud.tencent.com/developer/article/1840091]

## 4. [日志 slf4j](https://zhuanlan.zhihu.com/p/131401511)

    [这里使用的是log4j](https://cloud.tencent.com/developer/article/1782929)

1. 添加 Maven 依赖修改 POM.xml,
2. 排除默认的日志框架,

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <!-- 排除自带的logback依赖 -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j</artifactId>
    <version>1.3.8.RELEASE</version>
</dependency>
```
3. 新建log4j.properties

```properties
#设置控制台打印
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender     
#设置为格式化打印 PatternLayout
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout     
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n    

#设置info级别的日志
log4j.logger.info=info
#输出到日志文件
log4j.appender.info=org.apache.log4j.DailyRollingFileAppender
log4j.appender.info.layout=org.apache.log4j.PatternLayout     
log4j.appender.info.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
#日期文件名格式化
log4j.appender.info.datePattern='.'yyyy-MM-dd
log4j.appender.info.Threshold = info   
#是否追加
log4j.appender.info.append=true
#文件存放位置
log4j.appender.info.File=E:/dance/demo/log/info.log

log4j.logger.error=error  
log4j.appender.error=org.apache.log4j.DailyRollingFileAppender
log4j.appender.error.layout=org.apache.log4j.PatternLayout     
log4j.appender.error.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
log4j.appender.error.datePattern='.'yyyy-MM-dd
log4j.appender.error.Threshold = error   
log4j.appender.error.append=true
log4j.appender.error.File=E:/dance/demo/log/error.log

log4j.logger.DEBUG=DEBUG
log4j.appender.DEBUG=org.apache.log4j.DailyRollingFileAppender
log4j.appender.DEBUG.layout=org.apache.log4j.PatternLayout     
log4j.appender.DEBUG.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
log4j.appender.DEBUG.datePattern='.'yyyy-MM-dd
log4j.appender.DEBUG.Threshold = DEBUG   
log4j.appender.DEBUG.append=true
log4j.appender.DEBUG.File=E:/dance/demo/log/dubug.log
```

4. 修改配置文件application.yml

```
logging:
  # 设置logback.xml位置
#  config: classpath:log/logback.xml
  # 设置log4j.properties位置
  config: classpath:log4j.properties
```