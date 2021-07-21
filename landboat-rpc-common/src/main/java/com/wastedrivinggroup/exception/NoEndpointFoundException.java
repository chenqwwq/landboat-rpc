package com.wastedrivinggroup.exception;

import lombok.Getter;

/**
 * @author chen
 * @date 2021-07-21
 **/
@Getter
public class NoEndpointFoundException extends BaseException {

	private String serviceName;

	public NoEndpointFoundException(String serviceName) {
		this.serviceName = serviceName;
	}
}
