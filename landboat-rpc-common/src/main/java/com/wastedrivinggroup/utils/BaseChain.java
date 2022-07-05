package com.wastedrivinggroup.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author 沽酒
 * @since 2021/7/18
 **/
public abstract class BaseChain<O> {
	private final List<O> data;

	private Consumer<List<O>> afterAdd = os -> {
		// DEFAULT DO NOTHING
	};

	public BaseChain() {
		data = new ArrayList<>();
	}

	public BaseChain(List<O> data) {
		this.data = data;
	}

	public void add(O target) {
		data.add(target);
		afterAdd.accept(data);
	}

	public void remove(O target) {
		data.remove(target);
	}

	public void setAfterAdd(Consumer<List<O>> afterAdd) {
		this.afterAdd = afterAdd;
	}

	public List<O> getData(){
		return data;
	}
}
