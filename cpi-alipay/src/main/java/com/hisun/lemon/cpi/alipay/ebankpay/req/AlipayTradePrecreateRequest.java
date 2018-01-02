package com.hisun.lemon.cpi.alipay.ebankpay.req;

import com.hisun.channel.parse.annotation.Sign;
import com.hisun.lemon.cpi.alipay.ebankpay.util.AlipayHashMap;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 *  统一收单线下交易预创建
 */
public class AlipayTradePrecreateRequest implements AlipayRequest {
	private AlipayHashMap udfParams;
	private String apiVersion = "1.0";
	private String bizContent;
	private String terminalType;
	private String terminalInfo;
	private String prodCode;
	private String notifyUrl;
	private String returnUrl;
	/**
	 * 签名
	 */
	@Sign(alias = "sign",signauteClass="com.hisun.lemon.cpi.alipay.ebankpay.util.AlipaySignature")
	@NotNull
	private String sign;

	private boolean needEncrypt = false;
	private AlipayObject bizModel = null;

	public AlipayTradePrecreateRequest() {
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	public String getBizContent() {
		return this.bizContent;
	}

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getApiVersion() {
		return this.apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getTerminalType() {
		return this.terminalType;
	}

	public void setTerminalInfo(String terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public String getTerminalInfo() {
		return this.terminalInfo;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdCode() {
		return this.prodCode;
	}

	public String getApiMethodName() {
		return "alipay.trade.precreate";
	}

	public Map<String, String> getTextParams() {
		AlipayHashMap txtParams = new AlipayHashMap();
		txtParams.put("biz_content", this.bizContent);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}

		return txtParams;
	}

	public void putOtherTextParam(String key, String value) {
		if(this.udfParams == null) {
			this.udfParams = new AlipayHashMap();
		}

		this.udfParams.put(key, value);
	}

	public AlipayHashMap getUdfParams() {
		return udfParams;
	}

	public void setUdfParams(AlipayHashMap udfParams) {
		this.udfParams = udfParams;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean isNeedEncrypt() {
		return this.needEncrypt;
	}

	public void setNeedEncrypt(boolean needEncrypt) {
		this.needEncrypt = needEncrypt;
	}

	public AlipayObject getBizModel() {
		return this.bizModel;
	}

	public void setBizModel(AlipayObject bizModel) {
		this.bizModel = bizModel;
	}
}