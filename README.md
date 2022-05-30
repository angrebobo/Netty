## Netty教程  
视频教程看的是黑马程序员的Netty实战和尚硅谷的Netty，个人推荐看黑马程序员的就够了  
本项目完成了  
- BIO,NIO,IO多路复用的区别
- NIO中buffer，channel(FileChannel, SocketChannel, ServerSocketChannel, DatagramChannel)的使用方式
- Reactor设计模式(3种实现, 单Reactor单线程, 单Reactor多线程, 主从Reactor多线程)
- Netty的基础组件(EventLoop, Channel, Future, Promise, Handler, Pipeline)
- Netty实现简易Http服务器
- Netty心跳检测
- Netty粘包和半包的解决方法
- Netty协议设计
- Netty实现简易聊天室
- Netty实现简易RPC(用动态代理)

### Netty介绍
- Netty是一个异步的，基于事件驱动的网络应用框架，用以快速开发高性能，高可靠的网络IO程序
- Netty主要针对在TCP协议下，面向Client段的高并发应用，或者Peer-to-Peer场景下大量数据持续传输的应用
- Netty本质是一个NIO框架，适用于服务器通讯相关的多种应用场景