package com.wastedrivinggroup.service;

import java.lang.annotation.*;

/**
 * 声明调用的一级服务名称
 *
 * @author 沽酒
 * @since 2021/7/11
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    /**
     * @return 服务名称
     */
    String value() default "";

    /**
     * @return 服务地址
     */
    String url() default "";
}
