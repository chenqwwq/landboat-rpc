package com.wastedrivinggroup.service;

import java.lang.annotation.*;

/**
 * 声明调用的一级服务名称
 *
 * @author chen
 * @date 2021/7/11
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
	/**
	 * @return 服务名称
	 */
	String value();

	/**
	 * 优先于 {@link Func} 的异常处理
	 *
	 * @return 异常处理类
	 */
	Class<? extends ExceptionHandler> exceptionHandler() default DefaultExceptionHandler.class;

}
