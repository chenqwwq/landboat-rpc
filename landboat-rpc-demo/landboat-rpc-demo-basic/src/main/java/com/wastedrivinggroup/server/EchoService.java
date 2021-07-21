package com.wastedrivinggroup.server;

import com.wastedrivinggroup.provider.service.annotation.Expose;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
public class EchoService {

	@Expose("echo")
	public String echo(String sentence) {
		log.debug("echo from sentence:{}", sentence);
		return sentence;
	}
}
