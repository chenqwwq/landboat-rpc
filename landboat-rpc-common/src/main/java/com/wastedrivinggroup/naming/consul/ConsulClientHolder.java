package com.wastedrivinggroup.naming.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.wastedrivinggroup.utils.GracefulShutdown;
import com.wastedrivinggroup.utils.GracefulShutdownChain;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ConsulClientHolder {

	private static ConcurrentHashMap<HostAndPort, Consul> consulMap;

	static {
		// TODO: Consul 配置的读取
		consulMap = new ConcurrentHashMap<>();
		final HostAndPort hostAndPort = HostAndPort.fromString("localhost:8500");
		final Consul build = Consul.builder().withHostAndPort(hostAndPort).withPing(false).build();
		consulMap.put(hostAndPort, build);
	}

	private ConsulClientHolder() {
		GracefulShutdownChain.addShutdown(new GracefulShutdown() {
			@Override
			public void clear() {
				log.info("关闭Consul连接");
				consulMap.values().forEach(Consul::destroy);
			}
		});
	}

	public static boolean existClient() {
		return !consulMap.isEmpty();
	}

	// AgentClient

	public static List<AgentClient> getAgentClients() {
		if (consulMap.isEmpty()) {
			return Collections.emptyList();
		}
		return consulMap.values().stream()
				.map(Consul::agentClient)
				.collect(Collectors.toList());
	}

	public static Consul getConsul(HostAndPort hostAndPort) {
		final Consul build = Consul.builder().withHostAndPort(hostAndPort).withPing(false).build();
		consulMap.put(hostAndPort, build);
		return build;
	}

	public static AgentClient getAgentClient(String ip, int port) {
		return getAgentClient(HostAndPort.fromParts(ip, port));
	}

	public static AgentClient getAgentClient(HostAndPort hostAndPort) {
		if (consulMap.containsKey(hostAndPort)) {
			return consulMap.get(hostAndPort).agentClient();
		}
		return getConsul(hostAndPort).agentClient();
	}

	public static AgentClient getAgentClient(String url) {
		return getAgentClient(HostAndPort.fromString(url));
	}

	public static List<HealthClient> getHealthClient() {
		if (consulMap.isEmpty()) {
			return Collections.emptyList();
		}
		return consulMap.values().stream()
				.map(Consul::healthClient)
				.collect(Collectors.toList());
	}

	public static HealthClient getHealthClient(String ip, int port) {
		return getHealthClient(HostAndPort.fromParts(ip, port));
	}

	public static HealthClient getHealthClient(HostAndPort hostAndPort) {
		if (consulMap.containsKey(hostAndPort)) {
			return consulMap.get(hostAndPort).healthClient();
		}
		return getConsul(hostAndPort).healthClient();
	}

	public static HealthClient getHealthClient(String url) {
		return getHealthClient(HostAndPort.fromString(url));
	}


}
