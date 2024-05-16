# 一、启动准备

## 1.1、启动数据库（Docker）

- 安装Docker：[官网](https://www.docker.com/)

- 搜索Docker镜像：[DockerHub](https://hub.docker.com/)

- Docker教程推荐：[Docker快速入门到项目部署](https://www.bilibili.com/video/BV1HP4118797?p=1&vd_source=14dcfd5a96be2b05571b29387e1ac2cc)

- 启动Mysql

  ```shell
  docker run -d --name mysql -p 3306:3306 -e TZ=Asia/Shanghai -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.29
  ```

  - -d：detach mode，后台运行
  - --name：容器名
  - -p：port，端口映射，前面是宿主机端口，后面是容器内容mysql端口
  - -e：environment，环境变量，这里设置了TZ（时区）和MYSQL_ROOT_PASSWORD（数据库密码）
  - mysql:8.0.29：镜像名和版本

- 启动其它数据库：类似Mysql

## 1.2、启动数据库（Mac）

- 使用brew安装Mysql、Redis和Mongodb-community

- 数据库推荐版本

  - Mysql: 8.0.29
  - MongoDB: 5.0.7
  - Redis: 7.0.0

- 运行`scripts/start_database.sh`

  ```shell
  bash start_database.sh
  ```

## 1.3、启动和停止数据库（Windows）

- 启动Mysql
  - [下载地址](https://dev.mysql.com/downloads/mysql/)：下载安装版（msi）
  - 命令行启动：`net start MySQL81`
  - 命令行停止：`net stop MySQL81`
  - 这里我的服务名是`MySQL81`，`win+s`输入service，查看自己Mysql的服务名，也可以在服务里启动和停止Mysql
- 启动Mongodb
  - [下载地址](https://www.mongodb.com/try/download/community-kubernetes-operator)：下载安装版（msi）
  - [安装权限不足解决方案](https://www.zhihu.com/question/435851212/answer/3160284204?utm_id=0)
  - 命令行启动：`net start MongoDB`
  - 命令行停止：`net stop MongoDB`
  - 和Mysql同理，可以在服务里启动和停止MongoDB
- 启动Redis
  - [安装教程](https://learn.microsoft.com/en-us/windows/wsl/install)

## 2、修改配置文件

- 数据库配置文件位置：`src/main/resources/application-devDB.yml`
- 主要是注意端口以及密码配置

# 二、启动项目

- 注意：JDK不要用大于17的版本，会报错，代码不兼容

## 1、修改和创建配置文件

- 目前是将开发（本地和服务器）、测试以及生产的环境分开写的，主要是数据库的配置不同

- `application.yml`中的`spring.profiles.active`指定环境

- 为了保护数据库，`.gitignore`添加了`application-localDatabse.yml`、`application-devDatabse.yml`和`application-prodDatabse.yml`，所以这三个文件需要手动创建，目前都是在本地开发，所以只有`application-localDatabse.yml`是必须的，另外两个可以为空

- `application-localDatabse.yml`的内容如下：修改其中数据库的IP和端口（如果是用的Docker，就改成对应容器的IP地址），以及Mysql的密码

  ```yml
  # 数据库配置
  spring:
    cache:
      redis:
        time-to-live: 3600s
    data:
      mongodb:
        uri: mongodb://127.0.0.1:27017/exerciseSystem
    redis:
      host: localhost
      port: 6379
      database: 0
    datasource:
      druid:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/exercise_system_test?characterEncoding=UTF8&serverTimeZone=UTC&useSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: your-password
        druid:
          initial-size: 5
  
  mybatis:
    mapper-locations:
      - classpath:mappers/*.xml
      - classpath*:com/**/mappers/*.xml
  
  mybatis-plus:
    configuration:
      map-underscore-to-camel-case: false
  ```

## 2、导入数据

- 测试数据库连接：运行`src/test/java/com/dream/exerciseSystem/MongodbTest.java`里的`testMongodb`，不报错则连接成功
- 导入习题数据到MongoDB：运行`src/test/java/com/dream/exerciseSystem/MongodbTest.java`里的`javaProgramExercise2database`，不报错则导入成功

## 2、启动服务

- 开启所有需要的数据库
- 运行`src/main/java/com/dream/exerciseSystem/ExerciseSystemApplication.java`