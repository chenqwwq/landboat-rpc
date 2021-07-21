package com.wastedrivinggroup.env;

/**
 * @author chen
 * @date 2021-07-21
 **/
public interface InvokeCode {

	int SUCCESS = 1;

	/**
	 * 调用是否成功
	 *
	 * @param code 调用的返回码
	 * @return true -> 调用成功
	 */
	default boolean isSuccess(Integer code) {
		return code > 0;
	}

	interface ErrCode {

		int UNKNOWN_ERR = -1;

		/**
		 * 服务未找到
		 */
		int SERVICE_NOT_FOUND = -2;
	}
}
