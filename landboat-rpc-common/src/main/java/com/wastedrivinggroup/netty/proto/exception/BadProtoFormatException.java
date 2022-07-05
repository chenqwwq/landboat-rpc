package com.wastedrivinggroup.netty.proto.exception;

/**
 * @author 沽酒
 * @since 2021/5/4
 **/
public class BadProtoFormatException extends RuntimeException {
    public BadProtoFormatException(String message) {
        super(message);
    }
}
