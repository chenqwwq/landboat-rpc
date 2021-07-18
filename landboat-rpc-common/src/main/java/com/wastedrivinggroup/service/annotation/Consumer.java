package com.wastedrivinggroup.service.annotation;

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
public @interface Consumer {
	/**
	 * @return 服务名称
	 */
	String value();
}
