package com.hisun.lemon.cpi;

import com.alibaba.fastjson.JSONObject;
import com.hisun.lemon.cpi.alipay.ebankpay.*;
import com.hisun.lemon.cpi.alipay.ebankpay.req.AlipayAcquirePrecreateRequest;
import com.hisun.lemon.cpi.alipay.ebankpay.req.AlipayTradePrecreateRequest;
import com.hisun.lemon.cpi.alipay.ebankpay.req.AlipayTradePrecreateRequestBuilder;
import com.hisun.lemon.cpi.alipay.ebankpay.req.RequestParametersHolder;
import com.hisun.lemon.cpi.alipay.ebankpay.rsp.AlipayPrecreateResponse;
import com.hisun.lemon.cpi.alipay.ebankpay.AliPayEnumCommon.*;
import com.hisun.lemon.cpi.alipay.ebankpay.util.AlipayHashMap;
import com.hisun.lemon.cpi.alipay.ebankpay.util.AlipaySignature;
import com.hisun.lemon.cpi.alipay.ebankpay.util.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlipayRequestTest {

	@Resource
	private AlipayApi api;
	
	@Autowired
	private AlipayProperties alipayProperties;
	
	public static final String privateKey = "aliPay.merchantPrivateKey = MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQChv5OzzUtaXW0Ci6SA89PHqFuqftLSwh1VdtcU4SMNFs78JLkQCfgvO2xZuwZuLmRuO/p/+nyGMONNKkLAFFIxngkUVC9RqB+cB3OkS3pvnvXuVAqv7x3TwKpC5TOEIWwyDPFDlSdzMUzLFZqxsC3IzCg+T5TTZxx8ATtcdkzMsP3/I5qjkD7JS/kySQxCsdbiNh3kJlwzz/aLLorC073SQOEurCy/HZbDEYt3ZQ0vb7ivQaIqnMUcVpkV7TOYzCbrppAq1s2Kq24ZPLDlogT37yLwzM3b/R0BDUUBMqrcMCinVPMavCsjPJrWVMFSzIXGbAvEL2IrG6cxZXu2ypljAgMBAAECggEAR4WB10iY4E1dbXm39PlcFPYCCoLn0B/bfrX85xOiPyBtyBqo5kmyP5hkEc1cS44iBIhq/PoS4/dsGuvr9ilGKwB5fU64WcAgsgqtfOGPMxo0Hv10eFUgeEjCQLA+tlddE0ibykmC1zHehhJ4g8emhxqXVO41ncEDXGXjDMCWtw972h2ed+9sR0nZq233lMdyYfUh64N9XkbLxNoLH1ApWo7uyAq2hG2BtVdkOi3lyKS5I9tFl1/Q6gQr3vwcHw7gupZMWsyFFOgg3um0J/oVmDsFYBNgllvh24IX/lp/KDc2DRx+aFyrVpYKoWFcKUpGK2zXzWkstwkeJL4p7B5y0QKBgQDbTkjIrUQs/+A6yzX/EBtM23KndA2zpCvm4CeefwLOzmd5tBkB1bkU3XxPwBALb/lSlaf082y8Bjr2y/xthE5/FvLHy7AKXjKbjiQXjjnrTzxyO/R4HNo6/Vybm1Uq1j+GZmkxP8QPogcyA9SF3lh+2yZYM16ikx9cgRQTI1+o2QKBgQC8z+JnROlmnP5e3pVw6LWNyT95pdIsUnAFEgYsGJtL8TK6/tm/B6PUBNPujhpOKnkPTQ37G6F/Iuhaia/MgnuXHyekEm3r0u3JTXlW3lf23jMzolBEoPJGwPMkT1xaARh/zFfGTNRnM2wLtVcLHlFrlb3VL4Jhi5WGQe0DY4+OmwKBgEq0tzyNMX02yPaeRM2XegVLeBotAYHhegcS8sweLiGu1Db0L9SdLog6Dt/H6G9M/JyyWF2sEq1WXf22bU4NVQQcGCYRb+2IOyscV5UioP5Upp2vaM8F3R1dM4/acT+/bjJlbo4VT7+XjWXvQhU8Md883ioLjcBJ59QGMIV4dly5AoGAZuXUOPtxR57fccxUHQh1iSPYF++qrsTuj5TXEEkIuUZlo0soV9dODUnkRenjpB25hAE2UVcpmCPqBQ1sZUPnHuC3tc7lYNO/CynRqunZfoxx0v3uSLh90NluqKcnaVWXiG5Ql51fVC7N/0OAgGqxbLRalEgkAg0UG+rpxCMKJWkCgYBZOeXUUl09W0x3hr7ozmz7D8df2w66P0CbCPIovTneTis9bcQvfhUDjtl7+7W3D7/0+RloGOQPFr6p+1Sg+q0Hv/lEpoj3be/YY6Jf3T13j5buiPP79dkQUh/fNE4IJ8XOtKqXr4JGYMXQnM9xmCDH2EtHEkciSDwZCigZGJqwEw==";
	
	/**
	 * 用户扫码下单，聚合支付
	 */
	@Test
	public void userPlacePay() {
		String charset = null;
		String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
				+ (long) (Math.random() * 10000000L);

		// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
		String subject = "蒙肥羊自助火锅门店当面付扫码消费";

		// (必填) 订单总金额，单位为元，不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = "5.25";

		// (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
		String undiscountableAmount = "0";

		// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
		String sellerId = "";

		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
		String body = "购买商品3件共20.00元";

		// 商户操作员编号，添加此参数可以为商户操作员做销售统计
		String operatorId = "test_operator_id";

		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = "test_store_id";

		// 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId("2088102172435205");

		// 支付超时，定义为120分钟
		String timeoutExpress = "120m";

		// 商品明细列表，需填写购买商品详细信息，
		List<GoodsInfo.Goods_Detail> goodsDetailList = new ArrayList<GoodsInfo.Goods_Detail>();
		// 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
		GoodsInfo.Goods_Detail goods1 =new  GoodsInfo.Goods_Detail("goods_id001", "xxx小面包", 1000, 1);
		// 创建好一个商品后添加至商品明细列表
		goodsDetailList.add(goods1);

		// 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
		GoodsInfo.Goods_Detail goods2 = new  GoodsInfo.Goods_Detail("goods_id002", "xxx牙刷", 500, 2);
		goodsDetailList.add(goods2);

		// 创建扫码支付请求builder，设置请求参数
		AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
				.setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
				.setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
				.setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
				.setTimeoutExpress(timeoutExpress)
				.setNotifyUrl("http://dev.whoshell.com/alipay_demo/alipay/notify.html")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
				.setGoodsDetailList(goodsDetailList);
		AlipayTradePrecreateRequest precreateRequest = new AlipayTradePrecreateRequest();
		precreateRequest.setBizContent(builder.toJsonString());


		AlipayHashMap appParams = new AlipayHashMap(precreateRequest.getTextParams());
		try {
			// 仅当API包含biz_content参数且值为空时，序列化bizModel填充bizContent
			try {
				if (precreateRequest.getClass().getMethod("getBizContent") != null
						&& StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))
						&& precreateRequest.getBizModel() != null) {
					appParams.put(AlipayConstants.BIZ_CONTENT_KEY,builder.toJsonString());
				}
			} catch (NoSuchMethodException e) {
				// 找不到getBizContent则什么都不做
			} catch (SecurityException e) {

			}

			RequestParametersHolder requestHolder = new RequestParametersHolder();
			requestHolder.setApplicationParams(appParams);
			if (StringUtils.isEmpty(charset)) {
				charset = AlipayConstants.CHARSET_UTF8;
			}

			AlipayHashMap protocalMustParams = new AlipayHashMap();
			protocalMustParams.put(AlipayConstants.METHOD, precreateRequest.getApiMethodName());
			protocalMustParams.put(AlipayConstants.VERSION, precreateRequest.getApiVersion());
			protocalMustParams.put(AlipayConstants.APP_ID, "2088102172435205");
			protocalMustParams.put(AlipayConstants.SIGN_TYPE, AlipayConstants.SIGN_TYPE_RSA);
			protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, precreateRequest.getTerminalType());
			protocalMustParams.put(AlipayConstants.TERMINAL_INFO, precreateRequest.getTerminalInfo());
			protocalMustParams.put(AlipayConstants.NOTIFY_URL, precreateRequest.getNotifyUrl());
			protocalMustParams.put(AlipayConstants.RETURN_URL, precreateRequest.getReturnUrl());
			protocalMustParams.put(AlipayConstants.CHARSET, charset);

			Long timestamp = System.currentTimeMillis();
			DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
			df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
			protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
			requestHolder.setProtocalMustParams(protocalMustParams);

			AlipayHashMap protocalOptParams = new AlipayHashMap();
			protocalOptParams.put(AlipayConstants.FORMAT, AlipayConstants.FORMAT_JSON);
			protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, "");
			protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
			protocalOptParams.put(AlipayConstants.PROD_CODE, precreateRequest.getProdCode());
			requestHolder.setProtocalOptParams(protocalOptParams);

			String signContent = AlipaySignature.getSignatureContent(requestHolder);
			protocalMustParams.put(AlipayConstants.SIGN,
					AlipaySignature.rsaSign(signContent, privateKey, charset, AlipayConstants.SIGN_TYPE_RSA));

		}catch (Exception e ){

		}

		AlipayPrecreateResponse response = api.doSend(precreateRequest, EnumSource.tradePreCreate);
		System.out.println(response);
	}


	@Test
	public void precreate(){
		String charset = null;
		String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
				+ (long) (Math.random() * 10000000L);

		// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
		String subject = "TianHe";

		// (必填) 订单总金额，单位为元，不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = "5.25";

		// (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
		String undiscountableAmount = "0";

		// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
		String sellerId = "2088621889514248";

		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
		String body = "buy_two_total_20.00";

		// 商户操作员编号，添加此参数可以为商户操作员做销售统计
		String operatorId = "test_operator_id";

		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = "test_store_id";

		// 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		ExtendParams extendParams = new ExtendParams();

		// 支付超时，定义为120分钟
		String timeoutExpress = "120m";

		// 商品明细列表，需填写购买商品详细信息，
		List<GoodsInfo.Goods_Detail> goodsDetailList = new ArrayList<GoodsInfo.Goods_Detail>();
		// 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
		GoodsInfo.Goods_Detail goods1 =new  GoodsInfo.Goods_Detail("goods_id001", "xxx小面包", 1000, 1);
		// 创建好一个商品后添加至商品明细列表
		goodsDetailList.add(goods1);

		// 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
		GoodsInfo.Goods_Detail goods2 = new  GoodsInfo.Goods_Detail("goods_id002", "xxx牙刷", 500, 2);
		goodsDetailList.add(goods2);
		try{


		AlipayAcquirePrecreateRequest acquirePrecreateRequest = new AlipayAcquirePrecreateRequest();
		acquirePrecreateRequest.setService(EnumAlipayService.TRADE_PRECREATE.getCode());
		acquirePrecreateRequest.setBody(body);
		acquirePrecreateRequest.setSubject(subject);
		//acquirePrecreateRequest.setGoodsDetail(JSONObject.toJSONString(goodsDetailList));
		acquirePrecreateRequest.setTotal_fee(totalAmount);
		acquirePrecreateRequest.setTrans_currency("USD");
		acquirePrecreateRequest.setCurrency("USD");
		System.out.println(extendParams.toString());
		System.out.println(URLEncoder.encode(extendParams.toString()));
		acquirePrecreateRequest.setExtend_params(URLEncoder.encode(extendParams.toString(),"UTF-8"));
		acquirePrecreateRequest.setOut_trade_no(outTradeNo);
		acquirePrecreateRequest.setPrice(totalAmount);
		acquirePrecreateRequest.setQuantity("1");
		acquirePrecreateRequest.setSeller_id(sellerId);
		acquirePrecreateRequest.setProduct_code("OVERSEAS_MBARCODE_PAY");
		acquirePrecreateRequest.setTimestamp("1456507704121");
		AlipayPrecreateResponse response = api.doSend(acquirePrecreateRequest, EnumSource.tradePreCreate);
		System.out.println(response);

		}catch(Exception e){

		}
	}
	




}
