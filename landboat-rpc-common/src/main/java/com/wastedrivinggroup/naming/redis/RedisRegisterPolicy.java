package com.wastedrivinggroup.naming.redis;

import com.wastedrivinggroup.naming.AbstractRegisterPolicy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 沽酒
 * @since 2021-06-18
 **/
@Slf4j
public class RedisRegisterPolicy extends AbstractRegisterPolicy {

	@Override
	public void registered(String serviceName) {


	}

	@Override
	public void disRegistered(String serviceName) {

	}
}
