package com.wastedrivinggroup.provider.pojp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 服务元数据类
 *
 * // TODO: 可以增加 version 字段
 *
 * @author chen
 * @date 2021/7/21
 **/
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class ReflectFunction {

	/**
	 * 功能名称
	 */
	private final String funcName;

	/**
	 * 实际方法
	 */
	private final Method method;

	/**
	 * 实例对象
	 * <p>
	 * 不同的实例对象可能存在不同的返回方法
	 */
	private final Object object;
}
