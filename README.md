# ZhaZongChat
## 闸总聊天室——JavaSE项目网络聊天室+用户管理系统

*此项目采用C/S架构，MVC模式，GUI界面采用swing和AWT，分为2个项目文件，客户端zhazongchatclient，服务端zhazongchatserver。*

***基于swing、AWT、I/O流、多线程、socket、常用集合框架、反射机制、JDBC、饿汉式单例模式***

**开发环境JDK1.8，MySQL5.7，mysql-connector-java-5.1.47**

**运行环境:服务端/客户端Windows系统**

### 客户端

  客户端可以登录、注册、私聊、群聊功能。
  
  客户端在配置文件(config.properties)修改服务端的ip。
  
  客户端img文件为头像图片，注册时选择头像，登录后列表显示聊天室所有用户，当前登录用户名为绿色，上方按钮进入群聊，可以与其他用户实时私聊，用户不在线头像变黑能留言，收到信息好友名变蓝色提醒，实现多线程与多人同时进行聊天。

### 服务端

  服务端可以开启聊天服务与关闭服务，可进行互联网与局域网通信聊天，服务端主界面为用户管理系统，成功连接数据库主界面显示所有用户数据列表，可以对用户信息进行增删改查功能。
  
  服务端在配置文件(config.properties)修改数据库名、数据库ip端口、数据库用户名、数据库密码、主机ip。
  
  服务端导入MySQL文件，通过JDBC与MySQL连接，通过反射读取配置文件，主界面显示为数据库中用户表，能直接操作增删改查功能，开启服务后，通过信号类型判断用户登录、注册、实时聊天、留言。留言信息存储在数据库，对方接收到留言信息后删除。聊天为实时不保存聊天记录。
