package com.wastedrivinggroup.server;

import com.wastedrivinggroup.provider.service.annotation.Expose;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 沽酒
 * @since 2021/6/15
 **/
@Slf4j
public class EchoService {

	@Expose(value = "echo")
	public String echo(String sentence) {
		log.debug("echo from sentence:{}", sentence);
		return sentence;
	}
}
