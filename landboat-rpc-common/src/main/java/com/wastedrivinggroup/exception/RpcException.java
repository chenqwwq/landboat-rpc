package com.wastedrivinggroup.exception;

import com.wastedrivinggroup.exception.BaseException;
import lombok.Getter;

/**
 * @author chen
 * @date 2021/6/15
 **/
@Getter
public class RpcException extends BaseException {

    /**
     * 错误码
     * <p>
     * {@link com.wastedrivinggroup.env.InvokeCode }
     */
    private final int code;


    private final String name;


    public RpcException(int code, String funcName, String message) {
        super(message);
        this.code = code;
        this.name = funcName;
    }

    public RpcException(int code, String serviceName, String funcName, String message) {
        super(message);
        this.code = code;
        this.name = funcName;
    }
}
