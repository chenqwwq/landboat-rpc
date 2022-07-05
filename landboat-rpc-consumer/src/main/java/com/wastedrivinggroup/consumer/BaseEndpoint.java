package com.wastedrivinggroup.consumer;

import java.util.Objects;

/**
 * @author 沽酒
 * @since 2022/4/7
 **/
public class BaseEndpoint {
    /**
     * 主机地址
     */
    private final String host;

    /**
     * 主机端口
     */
    private final Integer port;

    private BaseEndpoint(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public static BaseEndpoint create(String host, Integer port) {
        // TODO: valid check
        return new BaseEndpoint(host, port);
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEndpoint that = (BaseEndpoint) o;
        return Objects.equals(host, that.host) && Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
