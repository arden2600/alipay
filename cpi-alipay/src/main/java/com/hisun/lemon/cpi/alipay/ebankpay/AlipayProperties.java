package com.hisun.lemon.cpi.alipay.ebankpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * User : Rui
 * Date : 2017/8/29
 * Time : 11:24
 **/
@Component
@ConfigurationProperties(prefix = "aliPay")
@PropertySource("classpath:config/ebankpay-alipay.properties")
public class AlipayProperties {
	//支付宝网关名
    private String openApiDomain;
	//合作方partnerId
    private String partnerId;

    private String appId;

    //商户私钥
    private String merchantPrivateKey;
	//商户公钥
	private String merchantPublicKey;

	//支付宝应用公钥
    private String aliPayAppKey;

    //签名类型 RSA->SHA1withRsa,RSA2->SHA256withRsa
    private String signType;

    //当面付最大查询次数和查询间隔（毫秒）
    private String maxQueryRetry;
    private String queryDuration;

    //当面付最大撤销次数和撤销间隔（毫秒）
    private String maxCancelRetry;
    private String cancelDuration;

	private String maxHeartbeatDelay;
	private String heartbeatDuration;

	public String getOpenApiDomain() {
		return openApiDomain;
	}

	public void setOpenApiDomain(String openApiDomain) {
		this.openApiDomain = openApiDomain;
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

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getMerchantPublicKey() {
        return merchantPublicKey;
    }

    public void setMerchantPublicKey(String merchantPublicKey) {
        this.merchantPublicKey = merchantPublicKey;
    }

    public String getAliPayAppKey() {
        return aliPayAppKey;
    }

    public void setAliPayAppKey(String aliPayAppKey) {
        this.aliPayAppKey = aliPayAppKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public void setMaxQueryRetry(String maxQueryRetry) {
        this.maxQueryRetry = maxQueryRetry;
    }

    public String getQueryDuration() {
        return queryDuration;
    }

    public void setQueryDuration(String queryDuration) {
        this.queryDuration = queryDuration;
    }

    public String getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public void setMaxCancelRetry(String maxCancelRetry) {
        this.maxCancelRetry = maxCancelRetry;
    }

    public String getCancelDuration() {
        return cancelDuration;
    }

    public void setCancelDuration(String cancelDuration) {
        this.cancelDuration = cancelDuration;
    }

    public String getMaxHeartbeatDelay() {
        return maxHeartbeatDelay;
    }

    public void setMaxHeartbeatDelay(String maxHeartbeatDelay) {
        this.maxHeartbeatDelay = maxHeartbeatDelay;
    }

    public String getHeartbeatDuration() {
        return heartbeatDuration;
    }

    public void setHeartbeatDuration(String heartbeatDuration) {
        this.heartbeatDuration = heartbeatDuration;
    }
}
