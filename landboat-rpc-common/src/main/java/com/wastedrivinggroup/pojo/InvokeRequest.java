package com.wastedrivinggroup.pojo;

import lombok.Data;

/**
 * @author chen
 * @date 2021/7/20
 **/
@Data
public class InvokeRequest {

	private Method method;

	private String funcName;

	private Object[] args;
}
