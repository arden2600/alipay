package com.hisun.lemon.cpi.alipay.ebankpay;


import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by liuyangkly on 15/6/26.
 * 扩展信息
 */
public class ExtendParams {
    private String secondary_merchant_id ="2088621889514248";

    private String secondary_merchant_name="forex_378199@alitest.com";

    private String secondary_merchant_industry="7011";

    private String store_name="Muku_in_the_Dreieichstrabe";

    private String store_id="BJ_ZZ_001";
    // 系统商编号
    private String terminal_id = "T80001";

    private String sys_service_provider_id = "R00998889911";

    @Override
    public String toString() {
        Map<String,String> contentMap = new HashMap<>();
        JSONObject object = new JSONObject();
        object.put("secondary_merchant_id","2088621889514248");
        object.put("secondary_merchant_name","forex_378199@alitest.com");
        object.put("secondary_merchant_industry","7011");
        object.put("store_name","Muku_in_the_Dreieichstrabe");
        object.put("store_id","BJ_ZZ_001");
        object.put("terminal_id","T80001");
        object.put("sys_service_provider_id","R00998889911");
        List<String> keys = new ArrayList(contentMap.keySet());
        Collections.sort(keys);
        for(String key : keys){
            object.put(key,contentMap.get(key));
        }
        return object.toJSONString();
    }

    public String getSysServiceProviderId() {
        return sys_service_provider_id;
    }

    public ExtendParams setSysServiceProviderId(String sysServiceProviderId) {
        this.sys_service_provider_id = sysServiceProviderId;
        return this;
    }

    public String getSecondary_merchant_id() {
        return secondary_merchant_id;
    }

    public void setSecondary_merchant_id(String secondary_merchant_id) {
        this.secondary_merchant_id = secondary_merchant_id;
    }

    public String getSecondary_merchant_name() {
        return secondary_merchant_name;
    }

    public void setSecondary_merchant_name(String secondary_merchant_name) {
        this.secondary_merchant_name = secondary_merchant_name;
    }

    public String getSecondary_merchant_industry() {
        return secondary_merchant_industry;
    }

    public void setSecondary_merchant_industry(String secondary_merchant_industry) {
        this.secondary_merchant_industry = secondary_merchant_industry;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getSys_service_provider_id() {
        return sys_service_provider_id;
    }

    public void setSys_service_provider_id(String sys_service_provider_id) {
        this.sys_service_provider_id = sys_service_provider_id;
    }
}
