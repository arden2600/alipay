package com.hisun.lemon.cpi.alipay.ebankpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hisun.channel.common.utils.StringUtils;

/**
 * 枚举公共类
 * 
 * @author zhangzl
 *
 */
@Controller
public class AliPayEnumCommon {

	private AliPayEnumCommon() {
	}

	/**
	 * 接口类型
	 * 
	 * @author zhangzl
	 *
	 */
	public enum EnumSource {
		tradePreCreate,
		tradeQuery
	}

	/**
	 * 账单类型
	 * 
	 * @author zhangzl
	 *
	 */
	public enum EnumAlipayBillType {
		/**
		 * 返回当日所有订单信息
		 */
		ALL,
		/**
		 * 返回当日成功支付的订单
		 */
		SUCCESS,
		/**
		 * 返回当日退款订单
		 */
		REFUND,
		/**
		 * 返回当日充值退款订单（相比其他对账单多一栏“返还手续费”）
		 */
		RECHARGE_REFUND

	}

	/**
	 * 交易返回码
	 * 
	 * @author zhangzl
	 *
	 */
	public enum EnumAlipayTrxStatus {
		SUCCESS("10000"),
		SERVICE_IS_NOT_AVAILABLE("20000"),
		INSUFFICIENT_AUTHORITY("20001"),
		MISSING_REQUIRED_PARAMETERS("40001"),
		ILLEGAL_PARAMETERS("40002"),
		BUSINESS_PROCESSING_FAILED("40004"),
		INSUFFICIENT_PERMISSIONS("40006");

		private String code;

		public static EnumAlipayTrxStatus getEnum(String code) {
			if (StringUtils.isNotEmpty(code)) {
				for (EnumAlipayTrxStatus trxStatus : values()) {
					if (StringUtils.equals(trxStatus.getCode(), code)) {
						return trxStatus;
					}
				}
			}
			return null;
		}

		EnumAlipayTrxStatus(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

	}

	/**
	 * 阿里支付接口服务名称
	 * 接口文档列表：https://docs.open.alipay.com/api_1/
	 * @author zhangzl
	 *
	 */
	public enum EnumAlipayService {

		TRADE_PRECREATE("alipay.acquire.precreate"),
		TRADE_QUERY("alipay.trade.query"),
		TRADE_CLOSE("alipay.trade.close"),
		TRADE_CANCEL("alipay.trade.cancel"),
		TRADE_REFUND("alipay.trade.refund"),
		TRADE_REFUND_QUERY("alipay.trade.fastpay.refund.query"),
		TRADE_CREATE("alipay.trade.create"),
		TRADE_PAY("alipay.trade.pay");

		private String code;

		public static EnumAlipayService getEnum(String code) {
			if (StringUtils.isNotEmpty(code)) {
				for (EnumAlipayService infCode : values()) {
					if (StringUtils.equals(infCode.getCode(), code)) {
						return infCode;
					}
				}
			}
			return null;
		}

		EnumAlipayService(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

	}


	/**
	 * 币种
	 *
	 * @author zhangzl
	 *
	 */
	public enum EnumCurrency {
		/**
		 * 人民币
		 */
		CNY,
		/**
		 * 港币
		 */
		HKD,
		/**
		 * 加币
		 */
		CAD,
		/**
		 * 美元
		 */
		USD,
		/**
		 * 欧元
		 */
		EUR,
		/**
		 * 英镑
		 */
		GBP,
		/**
		 * 澳元
		 */
		AUD,
		/**
		 * 新西兰元
		 */
		NZD,
		/**
		 * 日元
		 */
		JPY,
		/**
		 * 瑞士法郎
		 */
		CHF,
		/**
		 * 瑞典克朗
		 */
		SEK,
		/**
		 * 挪威克朗
		 */
		NOK,
		/**
		 * 新加坡元
		 */
		SGD,
		/**
		 * 泰国铢
		 */
		THB
	}

}
