/**
 * 服务注册和发现的相关类族
 * <p>
 * ## 服务的命名规则
 * <p>
 * 服务堆外提供一个统一的一级服务名称
 * <br>
 * 内部使用类名和方法名以及方法参数类型组成二级服务名称
 * <p>
 * ## 服务字典
 * <p>
 * 提供一个二级服务名称到真实服务方法的映射。
 * <br>
 * <p>
 * ## 服务注册
 * <p>
 * 将服务字典中的服务对外暴露，注册到一个或者多个注册中心。
 * <br>
 * <p>
 * ## 服务调用的提供方处理流程
 * <p>
 * 调用方根据一级服务名称寻找到对应的提供者的主机地址，然后以二级服务全名通过 Socket 发送到提供方。
 * <br>
 * 服务提供方根据二级服务名称定位到具体需要执行的方法，执行并返回对应结果，序列化并返回。
 *
 * <p>
 * {@link com.wastedrivinggroup.service.naming.RegisterPolicy} 为基础的服务注册接口
 * {@link com.wastedrivinggroup.service.naming.ServiceDiscovery} 为基础的服务发现接口
 * <p>
 * {@link com.wastedrivinggroup.service.naming.consul} 保存的是以 Consul 为目标的服务注册和发现逻辑
 * {@link com.wastedrivinggroup.service.naming.redis} 保存的是以 Redis 为目标的服务注册和发现逻辑
 *
 * @author chen
 * @date 2021/7/9
 **/
package com.wastedrivinggroup.service.naming;