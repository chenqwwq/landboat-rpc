package com.wastedrivinggroup.service;

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
	 * @param args 参数
	 * @return 返回值
	 * @throws Exception 包含如下:
	 *                   - InterruptedException 调用中断
	 */
	Object invoke(Object[] args) throws Exception;

}
