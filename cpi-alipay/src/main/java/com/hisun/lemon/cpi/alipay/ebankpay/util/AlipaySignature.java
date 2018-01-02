/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.hisun.lemon.cpi.alipay.ebankpay.util;

import com.hisun.channel.common.core.Signature;
import com.hisun.channel.common.lifecycle.LifecycleBase;
import com.hisun.channel.common.lifecycle.LifecycleState;
import com.hisun.channel.core.MessageContext;
import com.hisun.lemon.cpi.alipay.ebankpay.AliPayEnumCommon;
import com.hisun.lemon.cpi.alipay.ebankpay.AlipayApiException;
import com.hisun.lemon.cpi.alipay.ebankpay.AlipayConstants;
import com.hisun.lemon.cpi.alipay.ebankpay.req.AlipayAcquirePrecreateRequest;
import com.hisun.lemon.cpi.alipay.ebankpay.req.RequestParametersHolder;
import com.hisun.lemon.cpi.alipay.ebankpay.util.codec.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author runzhi
 */
public class AlipaySignature extends LifecycleBase implements Signature{

    private static final String privateKey_md5 = "t5zdi9f8ld33ndw6alblg24ncph7v053";
    private static final String privateKey_RSA = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMlzykylFLOHIOJeFbzP+ZTg2awZ3EeM6Bh/qxhNn7B3c4GwW+Mv5hOPYUXxXFAklls0wmMhAl2gh7gaLMoVqFx0Yg1pTE5KcBfgIH2FaxFs4xhH2RAoOcnujGQqDEX8rKwu38MqApmJP1TXWTiylTN2a3QJonf+jrsMA4C1OT91AgMBAAECgYBM5JSIEs7HA8IKhWz4p82VBQowxaIt8Vu51ilBWoekfMOq8dzw55yDRMwVPV5F/OEjKQ01dykHAbosDFmiPQgIq9VpFXpJz0n4V5QibH6vVCe+LKym8EPf0OsmEZIMxqPxkiCLaGumnJyCE7J0Te2zJiNMmU6UspV0q3IKOHsAlQJBAPxCc0nhrLKT01/cax0DWs/JrGx0fFrsqIyGVKkhFJr6qOvluCgkCygYUHM5QNhwbf5vbtgJCwtnfhKKqh/mQVcCQQDMcHsn/RsDFTPLNB4G7S9lM4OoHN9me85jb7HQwxfUsjR8W1dPsn/DSzzrvRFxbkUogPNZlMeEjewRDcZl8woTAkAkNG1Thz5ACxNlSL9e1KJt/CXxEu7eJeUy9fykoYjRjXQ9FyVNLY8kXAj/4JG7/rbqs5eXDgU1x87CXoB4P5XVAkEAueQhJ9hOYnw57zBBiVzL9sJjmU4/mElE/keGsONkXON5NAY+Gtqcr7BPAa+WF8UDn3O5UqAk1xPRLiSPXQ4j/wJAFaim7y72UKG7UveyGxQGh/7xv+6FIFAAhzWS/hF19uSkKtnJkQSXo363H62Y+DG9nLpANXPjFHZoUkEbf4l8RA==";
    /** RSA最大加密明文大小  */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** RSA最大解密密文大小   */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String getSignatureContent(RequestParametersHolder requestHolder) {
        return getSignContent(getSortedMap(requestHolder));
    }

    public static Map<String, String> getSortedMap(RequestParametersHolder requestHolder) {
        Map<String, String> sortedParams = new TreeMap<String, String>();
        AlipayHashMap appParams = requestHolder.getApplicationParams();
        if (appParams != null && appParams.size() > 0) {
            sortedParams.putAll(appParams);
        }
        AlipayHashMap protocalMustParams = requestHolder.getProtocalMustParams();
        if (protocalMustParams != null && protocalMustParams.size() > 0) {
            sortedParams.putAll(protocalMustParams);
        }
        AlipayHashMap protocalOptParams = requestHolder.getProtocalOptParams();
        if (protocalOptParams != null && protocalOptParams.size() > 0) {
            sortedParams.putAll(protocalOptParams);
        }

        return sortedParams;
    }

    @Override
    public String sign(String signData) {

        String rsaString = "";
       try {
            Object object = MessageContext.getCurrentContext().getRequest().getTarget();
            rsaString=pubSignMD5(object);
            //rsaString=pubSignRSA(object);
        } catch (Exception e) {
            logger.error("WEIXINSecurity.sign 加密异常：", e);
        }
        return rsaString;
    }

    private static String pubSignMD5(Object object) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieName = field.getName();
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            Object obj = field.get(object);
            if (obj != null) {
                if (fieName != "sign" && fieName != "sign_type") {
                    if(fieName.equals("extend_params")){
                     //   Object decodeObject = URLDecoder.decode(object.toString(),"utf-8");
              //          System.out.println(decodeObject.toString());
                        map.put(fieName,"{\"store_id\":\"BJ_ZZ_001\",\"sys_service_provider_id\":\"R00998889911\",\"secondary_merchant_industry\":\"7011\",\"secondary_merchant_name\":\"forex_378199@alitest.com\",\"store_name\":\"Muku_in_the_Dreieichstrabe\",\"terminal_id\":\"T80001\",\"secondary_merchant_id\":\"2088621889514248\"}");
                    }else{
                        map.put(fieName, obj.toString());
                    }

                    fieldNames.add(fieName);
                }
            }
            field.setAccessible(accessFlag);
        }
        StringBuilder paramStringBuffer = new StringBuilder();
        Collections.sort(fieldNames);
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = map.get(key);
            paramStringBuffer.append("&").append(key).append("=").append(value);
        }
        // 去掉请求字符串末尾的最后一个&号
        if (paramStringBuffer.indexOf("&", 0) == 0) {
            paramStringBuffer.deleteCharAt(0);
        }

        String signString=paramStringBuffer.append(privateKey_md5).toString();
        System.out.println("加密字符串：>>" + signString);
          String signstr = getMD5ofByte(signString.getBytes("utf-8")).toLowerCase();
        System.out.println("md5加签结果：>>" + signstr);
        return signstr;
    }


    private static String pubSignRSA(Object object) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieName = field.getName();
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            Object obj = field.get(object);
            if (obj != null) {
                if (fieName != "sign" && fieName != "sign_type") {
                    if(fieName.equals("extend_params")){
                        //Object decodeObject = URLDecoder.decode(object.toString(),"utf-8");
                        map.put(fieName,"{\"store_id\":\"BJ_ZZ_001\",\"sys_service_provider_id\":\"R00998889911\",\"secondary_merchant_industry\":\"7011\",\"secondary_merchant_name\":\"forex_378199@alitest.com\",\"store_name\":\"Muku_in_the_Dreieichstrabe\",\"terminal_id\":\"T80001\",\"secondary_merchant_id\":\"2088621889514248\"}");
                    }else{
                        map.put(fieName, obj.toString());
                    }

                    fieldNames.add(fieName);
                }
            }
            field.setAccessible(accessFlag);
        }
        StringBuilder paramStringBuffer = new StringBuilder();
        Collections.sort(fieldNames);
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = map.get(key);
            paramStringBuffer.append("&").append(key).append("=").append(value);
        }
        // 去掉请求字符串末尾的最后一个&号
        if (paramStringBuffer.indexOf("&", 0) == 0) {
            paramStringBuffer.deleteCharAt(0);
        }
        System.out.println("加密字原始字符串：>>" + paramStringBuffer.toString());
        String signStringMap=AlipaySignature.rsaSign(map,privateKey_RSA,AlipayConstants.CHARSET_UTF8);
        String signStringStr=AlipaySignature.rsaSign(paramStringBuffer.toString(),privateKey_RSA,AlipayConstants.CHARSET_UTF8);
     //   String signstr = getMD5ofByte(signString.getBytes("utf-8")).toLowerCase();
        System.out.println("RSA map 加签结果：>>" + signStringMap);
        System.out.println("RSA加签结果：>>" + signStringStr);
        return signStringStr;
    }



    private static String pubSign1(Object object) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieName = field.getName();
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            Object obj = field.get(object);
            if (obj != null) {
                if (fieName != "sign") {
                    map.put(fieName, obj.toString());
                    fieldNames.add(fieName);
                }
            }
            field.setAccessible(accessFlag);
        }
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        StringBuilder paramStringBuffer = new StringBuilder();
        Collections.sort(fieldNames);

        AlipayHashMap txtParams = new AlipayHashMap();
        txtParams.putAll(map);

        requestHolder.setApplicationParams(txtParams);


        AlipayHashMap protocalMustParams = new AlipayHashMap();
/*        protocalMustParams.put(AlipayConstants.METHOD, AliPayEnumCommon.EnumAlipayService.TRADE_PRECREATE.getCode());
        protocalMustParams.put(AlipayConstants.VERSION, "1.0");
        protocalMustParams.put(AlipayConstants.APP_ID, "2088102172435205");
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, AlipayConstants.SIGN_TYPE_RSA2);
        protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, "");
        protocalMustParams.put(AlipayConstants.TERMINAL_INFO, "");
        protocalMustParams.put(AlipayConstants.NOTIFY_URL, "");
        protocalMustParams.put(AlipayConstants.RETURN_URL, "");
        protocalMustParams.put(AlipayConstants.CHARSET, AlipayConstants.CHARSET_UTF8);*/

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, AlipayConstants.FORMAT_JSON);
        protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, "");
        protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        protocalOptParams.put(AlipayConstants.PROD_CODE, "OVERSEAS_MBARCODE_PAY");
        requestHolder.setProtocalOptParams(protocalOptParams);

        getSortedMap(requestHolder);
        String signContent = AlipaySignature.getSignatureContent(requestHolder);
        System.out.println("加密字符串：>>" + signContent);
        String signstr = AlipaySignature.rsaSign(signContent, privateKey_RSA, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
      //  String signstr = getMD5ofByte(signContent.getBytes("utf-8"));
        System.out.println("RSA加签结果：>>" + signstr);
        return signstr;
    }


    /**
     *
     * 获得MD5加密密码的方法
     */
    private static String getMD5ofByte(byte[] data) {
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
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr = null;
        int n = b;
        if (n < 0) {
            // 定义移位算法
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr.toUpperCase();
    }

    /**
     *
     * 处理字节数组得到MD5密码的方法
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }


    @Override
    public boolean verify(String verifyData, String signedData) {

        return true;
    }

    @Override
    protected void doInit() {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

    /**
     * 验签
     * @param obj 验签对象
     * @param sign 签名
     * @return
     */
    public static boolean isVerify(Object obj, String sign) {
        boolean bool = false;
/*        try {
            bool = com.hisun.channel.common.utils.StringUtils.contains(pubSign(obj), sign);
        } catch (Exception e) {
            logger.error("WEIXINSecurity.isVerify 验签异常：", e);
        }*/
        return bool;
    }

    /**
     * 
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     *  rsa内容签名
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws AlipayApiException
     */
    public static String rsaSign(String content, String privateKey, String charset,
                                 String signType) throws AlipayApiException {

        if (AlipayConstants.SIGN_TYPE_RSA.equals(signType)) {

            return rsaSign(content, privateKey, charset);
        } else if (AlipayConstants.SIGN_TYPE_RSA2.equals(signType)) {

            return rsa256Sign(content, privateKey, charset);
        } else {

            throw new AlipayApiException("Sign Type is Not Support : signType=" + signType);
        }

    }

    /**
     * sha256WithRsa 加签
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws AlipayApiException
     */
    public static String rsa256Sign(String content, String privateKey,
                                    String charset) throws AlipayApiException {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                .getInstance(AlipayConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new AlipayApiException("RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    /**
     * sha1WithRsa 加签
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws AlipayApiException
     */
    public static String rsaSign(String content, String privateKey,
                                 String charset) throws AlipayApiException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                .getInstance(AlipayConstants.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new AlipayApiException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            throw new AlipayApiException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    public static String rsaSign(Map<String, String> params, String privateKey,
                                 String charset) throws AlipayApiException {
        String signContent = getSignContent(params);

        return rsaSign(signContent, privateKey, charset);

    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
                                                    InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = StreamUtil.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");
        params.remove("sign_type");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static String getSignCheckContentV2(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset) throws AlipayApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }
    
    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset, String signType) throws AlipayApiException {
		String sign = params.get("sign");
		String content = getSignCheckContentV1(params);
		
		return rsaCheck(content, sign, publicKey, charset,signType);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey,
                                     String charset) throws AlipayApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }
    
    public static boolean rsaCheckV2(Map<String, String> params, String publicKey,
                                     String charset, String signType) throws AlipayApiException {
		String sign = params.get("sign");
		String content = getSignCheckContentV2(params);
		
		return rsaCheck(content, sign, publicKey, charset,signType);
	}

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset,
                                   String signType) throws AlipayApiException {

        if (AlipayConstants.SIGN_TYPE_RSA.equals(signType)) {

            return rsaCheckContent(content, sign, publicKey, charset);

        } else if (AlipayConstants.SIGN_TYPE_RSA2.equals(signType)) {

            return rsa256CheckContent(content, sign, publicKey, charset);

        } else {

            throw new AlipayApiException("Sign Type is Not Support : signType=" + signType);
        }

    }

    public static boolean rsa256CheckContent(String content, String sign, String publicKey,
                                             String charset) throws AlipayApiException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                .getInstance(AlipayConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new AlipayApiException(
                "RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static boolean rsaCheckContent(String content, String sign, String publicKey,
                                          String charset) throws AlipayApiException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                .getInstance(AlipayConstants.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new AlipayApiException(
                "RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm,
                                                 InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    /**
     * 验签并解密
     * <p>
     * <b>目前适用于公众号</b><br>
     * params参数示例：
     * <br>{
     *    <br>biz_content=M0qGiGz+8kIpxe8aF4geWJdBn0aBTuJRQItLHo9R7o5JGhpic/MIUjvXo2BLB++BbkSq2OsJCEQFDZ0zK5AJYwvBgeRX30gvEj6eXqXRt16/IkB9HzAccEqKmRHrZJ7PjQWE0KfvDAHsJqFIeMvEYk1Zei2QkwSQPlso7K0oheo/iT+HYE8aTATnkqD/ByD9iNDtGg38pCa2xnnns63abKsKoV8h0DfHWgPH62urGY7Pye3r9FCOXA2Ykm8X4/Bl1bWFN/PFCEJHWe/HXj8KJKjWMO6ttsoV0xRGfeyUO8agu6t587Dl5ux5zD/s8Lbg5QXygaOwo3Fz1G8EqmGhi4+soEIQb8DBYanQOS3X+m46tVqBGMw8Oe+hsyIMpsjwF4HaPKMr37zpW3fe7xOMuimbZ0wq53YP/jhQv6XWodjT3mL0H5ACqcsSn727B5ztquzCPiwrqyjUHjJQQefFTzOse8snaWNQTUsQS7aLsHq0FveGpSBYORyA90qPdiTjXIkVP7mAiYiAIWW9pCEC7F3XtViKTZ8FRMM9ySicfuAlf3jtap6v2KPMtQv70X+hlmzO/IXB6W0Ep8DovkF5rB4r/BJYJLw/6AS0LZM9w5JfnAZhfGM2rKzpfNsgpOgEZS1WleG4I2hoQC0nxg9IcP0Hs+nWIPkEUcYNaiXqeBc=,
     *    <br>sign=rlqgA8O+RzHBVYLyHmrbODVSANWPXf3pSrr82OCO/bm3upZiXSYrX5fZr6UBmG6BZRAydEyTIguEW6VRuAKjnaO/sOiR9BsSrOdXbD5Rhos/Xt7/mGUWbTOt/F+3W0/XLuDNmuYg1yIC/6hzkg44kgtdSTsQbOC9gWM7ayB4J4c=,
     *    sign_type=RSA,
     *    <br>charset=UTF-8
     * <br>}
     * </p>
     * @param params
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param isCheckSign     是否验签
     * @param isDecrypt       是否解密
     * @return 解密后明文，验签失败则异常抛出
     * @throws AlipayApiException 
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String alipayPublicKey,
                                             String cusPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt) throws AlipayApiException {
        String charset = params.get("charset");
        String bizContent = params.get("biz_content");
        if (isCheckSign) {
            if (!rsaCheckV2(params, alipayPublicKey, charset)) {
                throw new AlipayApiException("rsaCheck failure:rsaParams=" + params);
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, cusPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     * 加密并签名<br>
     * <b>目前适用于公众号</b>
     * @param bizContent      待加密、签名内容
     * @param alipayPublicKey 支付宝公钥
     * @param cusPrivateKey   商户私钥
     * @param charset         字符集，如UTF-8, GBK, GB2312
     * @param isEncrypt       是否加密，true-加密  false-不加密
     * @param isSign          是否签名，true-签名  false-不签名
     * @return 加密、签名后xml内容字符串
     * <p>
     * 返回示例：
     * <alipay>
     *  <response>密文</response>
     *  <encryption_type>RSA</encryption_type>
     *  <sign>sign</sign>
     *  <sign_type>RSA</sign_type>
     * </alipay>
     * </p>
     * @throws AlipayApiException 
     */
    public static String encryptAndSign(String bizContent, String alipayPublicKey,
                                        String cusPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign) throws AlipayApiException {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_GBK;
        }
        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        if (isEncrypt) {// 加密
            sb.append("<alipay>");
            String encrypted = rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if (isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>RSA</sign_type>");
            }
            sb.append("</alipay>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<alipay>");
            sb.append("<response>" + bizContent + "</response>");
            String sign = rsaSign(bizContent, cusPrivateKey, charset);
            sb.append("<sign>" + sign + "</sign>");
            sb.append("<sign_type>RSA</sign_type>");
            sb.append("</alipay>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }

    /**
     * 公钥加密
     * 
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     * @throws AlipayApiException
     */
    public static String rsaEncrypt(String content, String publicKey,
                                    String charset) throws AlipayApiException {
        try {
            PublicKey pubKey = getPublicKeyFromX509(AlipayConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance(AlipayConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes()
                : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密  
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();

            return StringUtils.isEmpty(charset) ? new String(encryptedData)
                : new String(encryptedData, charset);
        } catch (Exception e) {
            throw new AlipayApiException("EncryptContent = " + content + ",charset = " + charset,
                e);
        }
    }

    /**
     * 私钥解密
     * 
     * @param content    待解密内容
     * @param privateKey 私钥
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     * @throws AlipayApiException
     */
    public static String rsaDecrypt(String content, String privateKey,
                                    String charset) throws AlipayApiException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(AlipayConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance(AlipayConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset)
                ? Base64.decodeBase64(content.getBytes())
                : Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密  
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData)
                : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new AlipayApiException("EncodeContent = " + content + ",charset = " + charset, e);
        }
    }

    public static Map objectToMap(Object thisObj)
    {
        Map map = new HashMap();
        Class c;
        try
        {
            c = Class.forName(thisObj.getClass().getName());
            //获取所有的方法
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++)
            {   //获取方法名
                String method = m[i].getName();
                //获取get开始的方法名
                if (method.startsWith("get")&&!method.contains("getClass"))
                {
                    try{
                        //获取对应对应get方法的value值
                        Object value = m[i].invoke(thisObj);
                        if (value != null)
                        {
                            //截取get方法除get意外的字符 如getUserName-->UserName
                            String key=method.substring(3);
                            //将属性的第一个值转为小写
                            key=key.substring(0,1).toLowerCase()+key.substring(1);
                            //将属性key,value放入对象
                            map.put(key, value);
                        }
                    }catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("error:"+method);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }
}
