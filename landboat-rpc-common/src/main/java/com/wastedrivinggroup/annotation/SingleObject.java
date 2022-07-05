package com.wastedrivinggroup.annotation;

import java.lang.annotation.*;

/**
 * 单例对象
 *
 * @author 沽酒
 * @since 2021/7/20
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SingleObject {
}
