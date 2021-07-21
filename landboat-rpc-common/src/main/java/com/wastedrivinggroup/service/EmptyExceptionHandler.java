package com.wastedrivinggroup.service;

import com.wastedrivinggroup.exception.RpcException;

/**
 * 表示未设置任何的异常处理器
 *
 * @author chen
 * @date 2021-07-21
 **/
public class EmptyExceptionHandler implements ExceptionHandler {
	@Override
	public Object handle(RpcException exception) {
		// DO NOTHING
		return null;
	}
}
