package com.wastedrivinggroup.exception;

import com.wastedrivinggroup.env.InvokeCode;

/**
 * @author chen
 * @date 2021/6/15
 **/
public class ServiceNotFoundException extends RpcException {

	public ServiceNotFoundException(String serviceName) {
		super(InvokeCode.ErrCode.SERVICE_NOT_FOUND, serviceName, "服务未找到");
	}
}
