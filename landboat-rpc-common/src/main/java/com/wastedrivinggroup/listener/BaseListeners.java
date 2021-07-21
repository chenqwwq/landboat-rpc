package com.wastedrivinggroup.listener;

import com.wastedrivinggroup.utils.BaseChain;

import java.util.Comparator;

/**
 * 监听器链的基础实现
 *
 * @author chen
 * @date 2021/7/18
 **/
public class BaseListeners<O extends Listener> extends BaseChain<O> implements Listener {

	public BaseListeners() {
		super();
		setAfterAdd(os -> os.sort(Comparator.comparingInt(Listener::getPrior)));
	}
}
