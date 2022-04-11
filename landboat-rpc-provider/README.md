# 服务提供者的实现

Provider 启动的时候需要先加载本地的方法，根据 Expose 保存需要对外暴露的 FunctionDict。

FunctionDict 中包含 Name - Function 的映射，在接收到请求的时候根据 Name 获取到 Function 并执行。

Provider 中的主要概念：

Function 在 Provider 中对外暴露的方法都叫做 Function，初步使用 @Expose 标识，简单的实现就是 ReflectFunction，如下
```java
public final class ReflectFunction implements Function {

    /**
     * 功能名称
     */
    private final String funcName;

    /**
     * 实际方法
     */
    private final Method method;

    /**
     * 实例对象
     * <p>
     * 不同的实例对象可能存在不同的返回方法
     */
    private final Object object;
}
```

FunctionLoader 负责解析 Object 对象，提取出其中的 Functio。

FunctionDict 表示的是服务字典，持有所有的 Function，在接收到 Consumer 的时候，根据 name 匹配 Function。
