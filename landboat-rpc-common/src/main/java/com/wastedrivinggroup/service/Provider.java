package com.wastedrivinggroup.service;

import com.wastedrivinggroup.naming.RegisterPolicy;

import java.lang.annotation.*;

/**
 * @author chen
 * @date 2021-04-16
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Provider {

	Class<? extends RegisterPolicy> register();
}
