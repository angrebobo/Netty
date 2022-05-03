### Reactor模式的核心组成:
1. reactor: reactor在一个线程中运行，负责监听和分发事件，分发给适当的处理程序对IO事件做出反应
2. handle: 
3. 
根据Reactor的数量和处理线程的数量不同，可以分为3种类型
1. 单reactor单线程
2. 单reactor多线程
3. 主从reactor多线程


