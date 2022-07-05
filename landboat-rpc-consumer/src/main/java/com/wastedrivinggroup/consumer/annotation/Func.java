package com.wastedrivinggroup.consumer.annotation;

import com.wastedrivinggroup.consumer.exception.EmptyExceptionHandler;

import java.lang.annotation.*;

/**
 * 申明需要调用的方法名称
 *
 * @author 沽酒
 * @since 2021/7/14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Func {
    /**
     * @return 方法名称
     */
    String value();

    /**
     * 版本号
     *
     * @return -1表示不关心
     */
    long version() default -1;

    /**
     * 远程服务地址
     *
     * @return 返回 Http 地址
     */
    String url() default "";

    /**
     * @return 异常处理类
     */
    Class<?> exceptionHandle() default EmptyExceptionHandler.class;
}
