package com.wastedrivinggroup.consumer.exception;

import com.wastedrivinggroup.exception.RpcException;

/**
 * Rpc调用过程中的的异常处理器
 *
 * @author 沽酒
 * @since 2021-07-21
 **/
@FunctionalInterface
public interface ExceptionHandler {

	/**
	 * 异常处理方法
	 *
	 * @param exception {@link RpcException}
	 * @return 可返回可以不返回
	 */
	Object handle(RpcException exception);
}
