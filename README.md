# 一、启动准备

## 1.1、启动数据库（Mac）

- 使用brew安装Mysql、Redis和Mongodb-community

- 数据库推荐版本

  - Mysql: 8.0.29
  - MongoDB: 5.0.7
  - Redis: 7.0.0

- 运行`scripts/start_database.sh`

  ```shell
  bash start_database.sh
  ```

## 1.2、启动和停止数据库（Windows）

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

## 1、导入数据

- 测试数据库连接：运行`src/test/java/com/dream/exerciseSystem/MongodbTest.java`里的`testMongodb`，不报错则连接成功
- 导入习题数据到MongoDB：运行`src/test/java/com/dream/exerciseSystem/MongodbTest.java`里的`javaProgramExercise2database`，不报错则导入成功

## 2、启动服务

- 开启所有需要的数据库
- 运行`src/main/java/com/dream/exerciseSystem/ExerciseSystemApplication.java`