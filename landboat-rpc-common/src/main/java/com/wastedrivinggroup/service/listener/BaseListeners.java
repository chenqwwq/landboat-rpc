package com.wastedrivinggroup.service.listener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author chen
 * @date 2021/7/18
 **/
public class BaseListeners<L extends Listener> implements Listener {

	private final List<L> listeners;

	public BaseListeners() {
		listeners = new ArrayList<>();
	}

	public void add(L target) {
		listeners.add(target);
		listeners.sort(Comparator.comparingInt(Listener::getPrior));
	}

	public void remove(L target) {
		listeners.remove(target);
	}
}
