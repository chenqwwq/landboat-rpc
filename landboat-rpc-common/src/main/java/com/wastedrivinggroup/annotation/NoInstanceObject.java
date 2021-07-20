package com.wastedrivinggroup.annotation;

import java.lang.annotation.*;

/**
 * 该类可能不需要实例对象
 *
 * @author chen
 * @date 2021/7/20
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NoInstanceObject {
}