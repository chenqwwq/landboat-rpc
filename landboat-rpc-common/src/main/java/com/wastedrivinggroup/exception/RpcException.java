package com.wastedrivinggroup.exception;

import lombok.Getter;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Getter
public class RpcException extends BaseException {

	/**
	 * 错误码
	 * <p>
	 * {@link com.wastedrivinggroup.env.InvokeCode }
	 */
	private int code;

	private String serviceName;

	private String funcName;


	public RpcException(int code, String funcName, String message) {
		super(message);
		this.code = code;
		this.funcName = funcName;
	}

	public RpcException(int code, String serviceName, String funcName, String message) {
		super(message);
		this.code = code;
		this.serviceName = serviceName;
		this.funcName = funcName;
	}
}
