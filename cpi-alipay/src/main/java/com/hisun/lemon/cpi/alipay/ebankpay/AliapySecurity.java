package com.hisun.lemon.cpi.alipay.ebankpay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.hisun.channel.common.core.Signature;
import com.hisun.channel.common.lifecycle.LifecycleBase;
import com.hisun.channel.common.lifecycle.LifecycleState;
import com.hisun.channel.common.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 签名工具类
 * 
 * @author zhangzl
 *
 */
@Component
public class AliapySecurity extends LifecycleBase implements Signature {

	private static final Logger logger = LoggerFactory.getLogger(AliapySecurity.class);


	@Autowired
	AlipayProperties alipayProperties;

	@Override
	public String sign(String signStr) {
		System.out.println("签名内容:" + signStr);
		return signature(signStr);
	}

	@Override
	public boolean verify(String verifyStr, String signStr) {
		return wechatVerify(verifyStr, signStr);
	}

	@Override
	protected void doInit() {

	}

	@Override
	protected void doStart() {
		this.setState(LifecycleState.STARTING);
	}

	@Override
	protected void doStop() {
		this.setState(LifecycleState.STOPPING);
	}

	@Override
	protected void doDestroy() {

	}

	/**
	 * 签名
	 * 
	 * @param strData
	 *            签名内容
	 * @return
	 */
	public String signature(String strData) {
/*		try {
			logger.info("签名数据:" + strData + weChatProperties.getWeiXinAppKey());
			String md5String = getMD5ofByte((strData + weChatProperties.getWeiXinAppKey()).getBytes("UTF-8"));
			if (StringUtils.isBlank(md5String)) {
				return null;
			} else {
				return md5String.toLowerCase();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("WEIXINSecurity.signature 异常：", e);
			return null;
		}*/
		return null;
	}

	/**
	 * 验签
	 * 
	 * @param strData
	 *            加密签数据
	 * @param signaData
	 *            加密后签名
	 * @return
	 */
	public Boolean wechatVerify(String strData, String signaData) {
		Boolean bool = false;
		// 带验签字符串
		String strMsg = getBeforeSignmsg(strData);
		logger.info("WEIXINSecurity.verify 去掉SIGNEDMSG签名node:" + strMsg);
		String appKey = signature(strMsg);
		logger.info("WEIXINSecurity.verify 签名返回数据:" + appKey);
		if (StringUtils.equals(appKey, signaData)) {
			logger.info("WEIXINSecurity.verify 通过");
			bool = true;
		}
		return bool;
	}

	/**
	 * 去掉SIGNEDMSG签名node
	 * 
	 * @param xmlmsg
	 * @return
	 */
	private String getBeforeSignmsg(String xmlmsg) {
		if (xmlmsg == null) {
			return null;
		}
		int idxbegin = xmlmsg.indexOf("<SIGNEDMSG>");
		if (idxbegin == -1) {
			return xmlmsg;
		}
		String endstr = "</SIGNEDMSG>";
		int idxend = xmlmsg.indexOf(endstr);
		if (idxend == -1) {
			return xmlmsg;
		}
		StringBuilder sb = new StringBuilder(xmlmsg);
		sb.delete(idxbegin, idxend + endstr.length());
		int cphbegin = xmlmsg.indexOf("<VSPAPI>");
		if (cphbegin <= 0) {
            return sb.toString();
        }
		sb.delete(0, cphbegin);
		return sb.toString();
	}

	/**
	 * 
	 * 获得MD5加密密码的方法
	 */
	private String getMD5ofByte(byte[] data) {
		String origMD5 = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(data);
			origMD5 = byteArray2HexStr(result);
		} catch (Exception e) {
			logger.error("WEIXINSecurity.getMD5ofByte md5加密异常：", e);
		}
		return origMD5;
	}

	/**
	 * 
	 * 处理字节数组得到MD5密码的方法
	 */
	private String byteArray2HexStr(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			sb.append(byte2HexStr(b));
		}
		return sb.toString();
	}

	/**
	 * 
	 * 字节标准移位转十六进制方法
	 */
	private String byte2HexStr(byte b) {
		String hexStr = null;
		int n = b;
		if (n < 0) {
			// 定义移位算法
			n = b & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr.toUpperCase();
	}
}
