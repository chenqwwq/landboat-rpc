package com.wastedrivinggroup.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * @author chen
 * @date 2021/7/20
 **/
@Data
@Accessors(chain = true)
public class RpcRequest implements Request {

	private ServiceEndpoint endpoint;

	private Object[] args;

}
