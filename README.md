一个`WebSocketStomp`实例，服务端和客户端都是用Java实现。主要涉及`广播消息`和`点对象消息`。

### 导入项目
使用 idea 打开，下载maven依赖。

### 启动项目（服务端）
直接运行 `MyWebsocketStompApplication.class` 中的 `main` 方法

### 启动客户端
运行 `WebSocketStompClientTest` 中的 `clientTest` 方法，默认会启动两个客户端。

### 消息
1. `/app/hello` 接受客服端发送的消息
2. `/user/message` 客服端发消息给指定客户端
3. `/common` 服务端发送广播消息
