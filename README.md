# landboat-rpc

RPC framework

## TODO LIST

- ~~Consumer 调用形式的解耦，满足多协议调用~~
- ~~Consumer 中的 Channel 保存方法~~
- ~~Provider 的调用流程梳理~~
- Provider 和 Consumer 的监听器模块(Monitor 功能可能采用监听器的形式实现)
- Consumer 和 Provider 双端的注解字段明确
- LandBalance 模块，需要 Consumer 模块完善服务地址保存的方式
- Provider 端的异常处理形式统一，定义包含异常的 Response
- Netty 不同的序列化方式介入
- 支持异步和同步的调用方式

<br>

## 服务的命名

一个 Provider 应用对外提供一个统一的服务名称（ServiceName），一个服务中对外暴露的所有方法都称之为功能（FunctionName）。

Provider 向注册中心注册时，仅仅注册服务名称，功能名称 Provider 和 Consumer 协商。

Consumer 通过一个服务名称确定一个服务的远程地址，然后调用时发送具体的服务名称和参数。

## Consumer 的调用流程

Consumer 使用 JDK 的动态代理实现远程调用的功能，所以切入点也就在 InvocationHandler 的实现。

landboat-rpc 使用 InvokeProxy 作为动态代理的 InvocationHandler，每一个接口对应一个 InvocationHandler，创建时解析接口上的注解。

> Consumer 只能将服务声明到接口上，抽象类或普通类无法调用。

```mermaid
sequenceDiagram 

InvokeProxy ->> RpcInvokerDispatcher: 1. 获取 RpcInvoker
RpcInvokerDispatcher ->> RpcInvoker: 2. 创建 RpcInvoker(懒加载)
RpcInvokerDispatcher -->> InvokeProxy: 3. 返回 RpcInvoker
InvokeProxy ->> LoadBalance: 4. 获取服务器地址
InvokeProxy ->> RpcInvoker: 5. 创建 Request 并调用
RpcInvoker -->> InvokeProxy: 6. 调用成功直接返回
RpcInvoker -->> ExceptionHandler: 7. 异常处理

```
