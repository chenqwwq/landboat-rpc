package com.wastedrivinggroup.loadbalance;

import java.util.List;

/**
 * @author chen
 * @date 2021/7/19
 **/
public abstract class AbstractLoadBalance<T> extends ServiceList<T> implements LoadBalance<T> {


	public AbstractLoadBalance(List<T> services) {
		super(services);
	}
}
