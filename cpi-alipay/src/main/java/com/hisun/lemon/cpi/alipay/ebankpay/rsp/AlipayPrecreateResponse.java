package com.hisun.lemon.cpi.alipay.ebankpay.rsp;

import com.hisun.channel.parse.annotation.Item;
import com.hisun.channel.parse.annotation.Xml;

/**
 * Created by XianSky on 2017/12/27.
 */
@Xml(root = "xml", removeHead = true)
public class AlipayPrecreateResponse extends AlipayResponse{
    private static final long serialVersionUID = 2456278367459739544L;

    //@Item(cdata=true) CDATA
    @Item(alias="detail_error_code")
    private String detailErrorCode;

    @Item(alias="detail_error_des")
    private String detailErrorDes;

    @Item(alias="error")
    private String error;

    @Item(alias="is_success")
    private String isSuccess;

    @Item(alias="out_trade_no")
    private String outTradeNo;

    @Item(alias="pic_url")
    private String picUrl;

    @Item(alias="qr_code")
    private String qrCode;

    @Item(alias="result_code")
    private String resultCode;

    @Item(alias="trade_no")
    private String tradeNo;

    @Item(alias="voucher_type")
    private String voucherType;

    public AlipayPrecreateResponse() {
    }

    public void setDetailErrorCode(String detailErrorCode) {
        this.detailErrorCode = detailErrorCode;
    }

    public String getDetailErrorCode() {
        return this.detailErrorCode;
    }

    public void setDetailErrorDes(String detailErrorDes) {
        this.detailErrorDes = detailErrorDes;
    }

    public String getDetailErrorDes() {
        return this.detailErrorDes;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getIsSuccess() {
        return this.isSuccess;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return this.voucherType;
    }
}
