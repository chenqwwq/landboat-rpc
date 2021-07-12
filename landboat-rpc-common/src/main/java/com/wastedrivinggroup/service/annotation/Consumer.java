package com.wastedrivinggroup.service.annotation;

import java.lang.annotation.*;

/**
 * @author chen
 * @date 2021/7/11
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consumer {
	/**
	 * @return 服务名称
	 */
	String[] value();
}
