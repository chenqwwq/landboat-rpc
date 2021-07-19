package com.wastedrivinggroup.consumer.listener;

import com.wastedrivinggroup.utils.BaseChain;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 全局的监听器持有者
 * <p>
 * 观察者模式本来需要被观察者持有观察者的引用,
 * // TODO: 评估，是否由一个全局类持有可能更好处理一点
 *
 * @author chen
 * @date 2021/7/18
 **/
@Slf4j
public class InvokeListeners extends BaseChain<InvokerListener> implements InvokerListener {

	// Single Object

	private static final InvokeListeners INSTANCE = new InvokeListeners();

	private InvokeListeners() {
	}

	public static InvokeListeners getInstance() {
		return INSTANCE;
	}


	// Listener Executor

	@Override
	public void beforeInvoke(String name, String func, Method method, Object[] args) {
		for (InvokerListener listener : getData()) {
			listener.beforeInvoke(name, func, method, args);
		}
	}

	@Override
	public void afterSuccess(String name, String func, Object ret) {
		for (InvokerListener listener : getData()) {
			listener.afterSuccess(name, func, ret);
		}
	}

	@Override
	public void afterException(String name, String func, Object error) {
		for (InvokerListener listener : getData()) {
			listener.afterException(name, func, error);
		}
	}
}
