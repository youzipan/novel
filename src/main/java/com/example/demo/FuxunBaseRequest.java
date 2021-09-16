package com.example.demo;

import java.io.Serializable;

/**
 * @Title: FuxunBaseRequest
 * @ProjectName middle-ground
 * @Description: 付讯请求参数
 * @author Fengping.Pan
 * @date 2021/9/1 16:28
 */
public class FuxunBaseRequest implements Serializable{


    /**
     * API版本号，目前版本号为1.0
     */
    private String version;
    /**
     * 10位时间戳。若请求发起时间与平台服务端接受请求的时间相差过大，平台将直接拒绝本次请求
     */
    private Long timestamp;
    /**
     * 随机正整数。与timestamp联合使用以防止重放攻击
     */
    private String nonce;
    /**
     * 平台分配给第三方渠道的分销业务ID
     */
    private String partnerId;
    /**
     * 平台分配给第三方渠道的应用Id
     */
    private String appId;
    /**
     * 用于验证此次请求合法性的签名（签名方法见下文）
     */
    private String signature;
    /**
     * 具体业务数据
     */
    private String data;

    public FuxunBaseRequest() {
    }

    public FuxunBaseRequest(String data) {
        this.data = data;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
