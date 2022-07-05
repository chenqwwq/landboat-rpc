package com.wastedrivinggroup.netty.proto.demo;

import com.wastedrivinggroup.env.InvokeCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 调用的结果
 *
 * @author 沽酒
 * @since 2021/6/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvokeRespProto {
    /**
     * 调用码,表示是否调用成功
     */
    private Integer invokeCode;

    /**
     * 请求的编号
     */
    private Long invokeId;

    /**
     * 错误信息
     */
    private String errMemo;

    /**
     * 正常返回
     * FIXME: 使用 Object 类型作为返回值，服务端传出为 Integer,但是客户端接收 gson 解码后为 Double,目前是直接以 String 传递返回结果,根据结果类型再次用 gson 解码
     */
    private String ret;

    public static InvokeRespProto ofSuccess(Long invokeId, String ret) {
        return ofSuccess(InvokeCode.SUCCESS, invokeId, ret);
    }

    public static InvokeRespProto ofSuccess(Integer invokeCode, Long invokeId, String ret) {
        return new InvokeRespProto()
                .setInvokeId(invokeId)
                .setInvokeCode(invokeCode)
                .setRet(ret);
    }

    public static InvokeRespProto ofFailure(Integer invokeCode, Long invokeId, String errMemo) {
        return new InvokeRespProto()
                .setInvokeId(invokeId)
                .setErrMemo(errMemo)
                .setInvokeCode(invokeCode);
    }

}
