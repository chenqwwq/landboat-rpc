package com.wastedrivinggroup.service;

import java.lang.annotation.*;

/**
 * @author chen
 * @date 2021/7/14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Func {
	/**
	 * @return 二级服务名称
	 */
	String value();

	/**
	 * @return 异常处理类
	 */
	Class<?> exceptionHandle() default EmptyExceptionHandler.class;
}
