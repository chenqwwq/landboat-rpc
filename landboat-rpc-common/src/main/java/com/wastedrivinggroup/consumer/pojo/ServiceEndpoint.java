package com.wastedrivinggroup.consumer.pojo;

import com.orbitz.consul.model.health.Service;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 服务地址
 * <p>
 *
 * @author chen
 * @date 2021-06-18
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ServiceEndpoint {
	/**
	 * 主机地址
	 */
	private String host;

	/**
	 * 主机端口
	 */
	private Integer port;

	/**
	 * 优先级
	 * <p>
	 * 取0~10,默认为5,越小优先级越高
	 */
	private Integer priority = 5;

	public ServiceEndpoint(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public static ServiceEndpoint tran2(Service service) {
		return new ServiceEndpoint(service.getAddress(), service.getPort());
	}

	public ServiceEndpoint(String host, Integer port, Integer priority) {
		checkPriority(priority);
		this.host = host;
		this.port = port;
		this.priority = checkPriority(priority);
	}

	public void setPriority(Integer priority) {

		this.priority = checkPriority(priority);
	}

	private Integer checkPriority(Integer priority) {
		if (Objects.isNull(priority)) {
			throw new IllegalArgumentException("priority is null ");
		}
		if (priority < 0 || priority > 10) {
			throw new IllegalArgumentException("bad value for priority: " + priority);
		}
		return priority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ServiceEndpoint that = (ServiceEndpoint) o;
		return host.equals(that.host) && port.equals(that.port);
	}

	@Override
	public int hashCode() {
		return Objects.hash(host, port);
	}
}
