package com.wastedrivinggroup.consumer.rpc;

import java.lang.annotation.*;

/**
 * @author chen
 * @date 2021/7/14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {
	/**
	 * @return 二级服务名称
	 */
	String value();
}
