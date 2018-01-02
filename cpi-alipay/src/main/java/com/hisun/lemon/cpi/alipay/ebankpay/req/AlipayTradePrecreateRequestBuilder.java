package com.hisun.lemon.cpi.alipay.ebankpay.req;

import com.hisun.lemon.cpi.alipay.ebankpay.ExtendParams;
import com.hisun.lemon.cpi.alipay.ebankpay.GoodsInfo;
import com.hisun.lemon.cpi.alipay.ebankpay.util.StringUtils;

import java.util.List;

/**
 * Created by liuyangkly on 16/3/3.
 */
public class AlipayTradePrecreateRequestBuilder extends RequestBuilder {

    private BizContent bizContent = new BizContent();

    @Override
    public BizContent getBizContent() {
        return bizContent;
    }

    @Override
    public boolean validate() {
        if (StringUtils.isEmpty(bizContent.out_trade_no)) {
            throw new NullPointerException("out_trade_no should not be NULL!");
        }
        if (StringUtils.isEmpty(bizContent.total_amount)) {
            throw new NullPointerException("total_amount should not be NULL!");
        }
        if (StringUtils.isEmpty(bizContent.subject)) {
            throw new NullPointerException("subject should not be NULL!");
        }
        if (StringUtils.isEmpty(bizContent.store_id)) {
            throw new NullPointerException("store_id should not be NULL!");
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlipayTradePrecreateRequestBuilder{");
        sb.append("bizContent=").append(bizContent);
        sb.append(", super=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public AlipayTradePrecreateRequestBuilder setAppAuthToken(String appAuthToken) {
        return (AlipayTradePrecreateRequestBuilder) super.setAppAuthToken(appAuthToken);
    }

    @Override
    public AlipayTradePrecreateRequestBuilder setNotifyUrl(String notifyUrl) {
        return (AlipayTradePrecreateRequestBuilder) super.setNotifyUrl(notifyUrl);
    }

    public String getOutTradeNo() {
        return bizContent.out_trade_no;
    }

    public AlipayTradePrecreateRequestBuilder setOutTradeNo(String outTradeNo) {
        bizContent.out_trade_no = outTradeNo;
        return this;
    }

    public String getSellerId() {
        return bizContent.seller_id;
    }

    public AlipayTradePrecreateRequestBuilder setSellerId(String sellerId) {
        bizContent.seller_id = sellerId;
        return this;
    }

    public String getTotalAmount() {
        return bizContent.total_amount;
    }

    public AlipayTradePrecreateRequestBuilder setTotalAmount(String totalAmount) {
        bizContent.total_amount = totalAmount;
        return this;
    }

    public String getDiscountableAmount() {
        return bizContent.discountable_amount;
    }

    public AlipayTradePrecreateRequestBuilder setDiscountableAmount(String discountableAmount) {
        bizContent.discountable_amount = discountableAmount;
        return this;
    }

    public String getUndiscountableAmount() {
        return bizContent.undiscountable_amount;
    }

    public AlipayTradePrecreateRequestBuilder setUndiscountableAmount(String undiscountableAmount) {
        bizContent.undiscountable_amount = undiscountableAmount;
        return this;
    }

    public String getSubject() {
        return bizContent.subject;
    }

    public AlipayTradePrecreateRequestBuilder setSubject(String subject) {
        bizContent.subject = subject;
        return this;
    }

    public String getBody() {
        return bizContent.body;
    }

    public AlipayTradePrecreateRequestBuilder setBody(String body) {
        bizContent.body = body;
        return this;
    }

    public List<GoodsInfo.Goods_Detail> getGoodsDetailList() {
        return bizContent.goods_detail;
    }

    public AlipayTradePrecreateRequestBuilder setGoodsDetailList(List<GoodsInfo.Goods_Detail> goodsDetailList) {
        bizContent.goods_detail = goodsDetailList;
        return this;
    }

    public String getOperatorId() {
        return bizContent.operator_id;
    }

    public AlipayTradePrecreateRequestBuilder setOperatorId(String operatorId) {
        bizContent.operator_id = operatorId;
        return this;
    }

    public String getStoreId() {
        return bizContent.store_id;
    }

    public AlipayTradePrecreateRequestBuilder setStoreId(String storeId) {
        bizContent.store_id = storeId;
        return this;
    }

    public String getAlipayStoreId() {
        return bizContent.alipay_store_id;
    }

    public AlipayTradePrecreateRequestBuilder setAlipayStoreId(String alipayStoreId) {
        bizContent.alipay_store_id = alipayStoreId;
        return this;
    }

    public String getTerminalId() {
        return bizContent.terminal_id;
    }

    public AlipayTradePrecreateRequestBuilder setTerminalId(String terminalId) {
        bizContent.terminal_id = terminalId;
        return this;
    }

    public ExtendParams getExtendParams() {
        return bizContent.extend_params;
    }

    public AlipayTradePrecreateRequestBuilder setExtendParams(ExtendParams extendParams) {
        bizContent.extend_params = extendParams;
        return this;
    }

    public String getTimeoutExpress() {
        return bizContent.timeout_express;
    }

    public AlipayTradePrecreateRequestBuilder setTimeoutExpress(String timeoutExpress) {
        bizContent.timeout_express = timeoutExpress;
        return this;
    }

    public static class BizContent {
        // 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        private String out_trade_no;

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        private String seller_id;

        // 订单总金额，整形，此处单位为元，精确到小数点后2位，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        private String total_amount;

        // 订单可打折金额，此处单位为元，精确到小数点后2位
        // 可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        private String discountable_amount;

        // 订单不可打折金额，此处单位为元，精确到小数点后2位，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        private String undiscountable_amount;

        // 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
        private String subject;

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        private String body;

        // 商品明细列表，需填写购买商品详细信息，
        private List<GoodsInfo.Goods_Detail> goods_detail;

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        private String operator_id;

        // 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        private String store_id;

        // 支付宝商家平台中配置的商户门店号，详询支付宝技术支持
        private String alipay_store_id;

        // 商户机具终端编号，当以机具方式接入支付宝时必传，详询支付宝技术支持
        private String terminal_id;

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        private ExtendParams extend_params;

        // (推荐使用，相对时间) 支付超时时间，5m 5分钟
        private String timeout_express;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("BizContent{");
            sb.append("outTradeNo='").append(out_trade_no).append('\'');
            sb.append(", sellerId='").append(seller_id).append('\'');
            sb.append(", totalAmount='").append(total_amount).append('\'');
            sb.append(", discountableAmount='").append(discountable_amount).append('\'');
            sb.append(", undiscountableAmount='").append(undiscountable_amount).append('\'');
            sb.append(", subject='").append(subject).append('\'');
            sb.append(", body='").append(body).append('\'');
            sb.append(", goodsDetailList=").append(goods_detail);
            sb.append(", operatorId='").append(operator_id).append('\'');
            sb.append(", storeId='").append(store_id).append('\'');
            sb.append(", alipayStoreId='").append(alipay_store_id).append('\'');
            sb.append(", terminalId='").append(terminal_id).append('\'');
            sb.append(", extendParams=").append(extend_params);
            sb.append(", timeoutExpress='").append(timeout_express).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
