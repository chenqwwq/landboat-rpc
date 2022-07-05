/**
 * 负载均衡相关类
 * <p>
 * 参考 Ribbon 实现:
 * 1. 轮询
 * 2. 随机
 * 3. 一致性 Hash
 * 4. 基础 Hash
 * 5. 加权轮询
 * <p>
 * <p>
 * 负载均衡需要持有对应的服务端列表，所以提供了 {@link com.wastedrivinggroup.loadbalance.ServiceList}
 * 这里保存的就是服务端的远程地址，可能是 {@link com.wastedrivinggroup.pojo.ServiceEndpoint},
 * 具体需要对地址做什么根据 {@link com.wastedrivinggroup.service.RpcInvoker} 来确定
 * <p>
 * // TODO: 因为仅保存地址可能无法确定对应服务端的健康状况
 * <p>
 * 服务列表的同步可以使用观察者模式监听 {@link com.wastedrivinggroup.service.ServiceCenter} 的变化
 * 或者直接持有 {@link com.wastedrivinggroup.service.ServiceCenter} 的服务列表的引用
 *
 * @author 沽酒
 * @since 2021/7/19
 **/
package com.wastedrivinggroup.loadbalance;