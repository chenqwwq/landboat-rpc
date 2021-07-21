package com.wastedrivinggroup.provider.service.annotation;

import java.lang.annotation.*;

/**
 * 暴露服务的注解
 *
 * @author chen
 * @date 2021/7/18
 **/
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Expose {

	/**
	 * @return 功能名称
	 */
	String value();

}
