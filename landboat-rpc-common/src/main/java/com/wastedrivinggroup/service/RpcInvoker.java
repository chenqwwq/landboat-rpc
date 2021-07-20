package com.wastedrivinggroup.service;

import com.wastedrivinggroup.pojo.InvokeRequest;
import com.wastedrivinggroup.pojo.InvokeResponse;

/**
 * 执行具体的 Rpc 调用接口
 * <p>
 *
 * @author chen
 * @date 2021/7/13
 **/
@FunctionalInterface
public interface RpcInvoker {

	/**
	 * 远程调用方法
	 *
	 * @param request 远程调用请求，{@link InvokeRequest}
	 * @return 返回值 {@link InvokeResponse} 返回值
	 * @throws Exception 包含如下:
	 *                   - InterruptedException 调用中断
	 */
	InvokeResponse invoke(InvokeRequest request) throws Exception;

}
