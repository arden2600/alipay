package com.hisun.lemon.cpi.alipay.ebankpay.req;

import com.hisun.channel.parse.annotation.*;
import com.hisun.lemon.cpi.alipay.ebankpay.AliPayEnumCommon;
import com.hisun.lemon.cpi.alipay.ebankpay.AlipayConstants;
import com.hisun.lemon.cpi.alipay.ebankpay.util.AlipayHashMap;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by XianSky on 2017/12/28.
 */
@Plain(form = Plain.Form.QUERY_STRING)
//@OrderByLetter
public class AlipayAcquirePrecreateRequest{

    @Item
    private AlipayHashMap udfParams; // add user-defined text parameters

    @Item
    @SignValue
    private String service = AliPayEnumCommon.EnumAlipayService.TRADE_PRECREATE.getCode();

    @Item
    @SignValue
    private String partner="2088621889514248";

    @Item
    @SignValue
    private String _input_charset= AlipayConstants.CHARSET_UTF8;

    @Item
    @SignValue
    private String sign_type = "MD5";
    //private String sign_type = AlipayConstants.SIGN_TYPE_RSA;

    /**
     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     */
    @Item
    @SignValue
    private String body;


    /**
     * 订单金额币种。目前只支持传入156（人民币）。
     如果为空，则默认设置为156
     */
    @Item
    @SignValue
    private String currency;

    /**
     * 公用业务扩展信息。用于商户的特定业务信息的传递，只有商户与支付宝约定了传递此参数且约定了参数含义，此参数才有效。
     比如可传递二维码支付场景下的门店ID等信息，以json格式传输。
     */
    @Item
    @SignValue
    private String extend_params;

    /**
     * 描述商品明细信息，json格式。
     */
    @Item
    @SignValue
    private String goodsDetail;

    @Item
    @SignValue
    private String product_code;


    /**
     * 支付宝合作商户网站唯一订单号
     */
    @Item
    @SignValue
    private String out_trade_no;

    /**
     * 订单中商品的单价。
     如果请求时传入本参数，则必须满足total_fee=price×quantity的条件
     */
    @Item
    @SignValue
    private String price;

    /**
     * 订单中商品的数量。
     如果请求时传入本参数，则必须满足total_fee=price×quantity的条件
     */
    @Item
    @SignValue
    private String quantity;

    /**
     * 分账信息。
     描述分账明细信息，json格式
     */
    @Item
    @SignValue
    private String notify_url;

    /**
     * 分账类型。卖家的分账类型，目前只支持传入ROYALTY（普通分账类型）
     */
    @Item
    @SignValue
    private String timestamp;


    /**
     * 卖家支付宝账号对应的支付宝唯一用户号，以2088开头的纯16位数字。如果和seller_email同时为空，则本参数默认填充partner的值
     */
    @Item
    @SignValue
    private String seller_id;

    /**
     * 收银台页面上，商品展示的超链接
     */
    @Item
    @SignValue
    private String show_url;

    /**
     * 商品购买
     */
    @Item
    @SignValue
    private String subject;

    /**
     * 订单金额。该笔订单的资金总额，取值范围[0.01,100000000]，精确到小数点后2位。
     */
    @Item
    @SignValue
    private String total_fee;

    @Item
    @SignValue
    private String trans_currency;

    /**
     * 签名
     */
    @Sign(alias = "sign",place= Sign.Place.FORM,signauteClass="com.hisun.lemon.cpi.alipay.ebankpay.util.AlipaySignature")
    @NotNull
    private String sign;

    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return this.body;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return this.currency;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
    public String getGoodsDetail() {
        return this.goodsDetail;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return this.price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getQuantity() {
        return this.quantity;
    }



    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return this.subject;
    }


    public String getApiMethodName() {
        return "alipay.acquire.overseas.spot.pay";
    }

    public Map<String, String> getTextParams() {
        AlipayHashMap txtParams = new AlipayHashMap();
        txtParams.put("body", this.body);
        txtParams.put("currency", this.currency);
        txtParams.put("extend_params", this.extend_params);
        txtParams.put("goods_detail", this.goodsDetail);
        txtParams.put("out_trade_no", this.out_trade_no);
        txtParams.put("price", this.price);
        txtParams.put("quantity", this.quantity);
        txtParams.put("subject", this.subject);
        txtParams.put("total_fee", this.total_fee);
        if(udfParams != null) {
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

    public String getTrans_currency() {
        return trans_currency;
    }

    public void setTrans_currency(String trans_currency) {
        this.trans_currency = trans_currency;
    }


    public AlipayHashMap getUdfParams() {
        return udfParams;
    }

    public void setUdfParams(AlipayHashMap udfParams) {
        this.udfParams = udfParams;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(String extend_params) {
        this.extend_params = extend_params;
    }


    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

}
