package com.wastedrivinggroup.loadbalance;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 轮询的负载均衡策略
 * // TODO: 暂时先意思意思吧
 *
 * @author 沽酒
 * @since 2021/7/20
 **/
@Slf4j
public class RoundRobinLoadBalance<T> extends AbstractLoadBalance<T> {

	public RoundRobinLoadBalance(List<T> services) {
		super(services);
	}

	@Override
	public T choose() {
		return getServices().get(0);
	}
}
