package com.wastedrivinggroup.service;

import com.wastedrivinggroup.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认直接抛出异常
 *
 * @author chen
 * @date 2021-07-21
 **/
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public Object handle(RpcException exception) {
		throw exception;
	}
}
