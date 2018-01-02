package com.hisun.lemon.cpi.alipay.ebankpay.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by XianSky on 2017/12/27.
 */
public class AlipayHashMap extends HashMap<String, String> {
    private static final long serialVersionUID = -1277791390393392630L;

    public AlipayHashMap() {
    }

    public AlipayHashMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String put(String key, Object value) {
        String strValue;
        if(value == null) {
            strValue = null;
        } else if(value instanceof String) {
            strValue = (String)value;
        } else if(value instanceof Integer) {
            strValue = ((Integer)value).toString();
        } else if(value instanceof Long) {
            strValue = ((Long)value).toString();
        } else if(value instanceof Float) {
            strValue = ((Float)value).toString();
        } else if(value instanceof Double) {
            strValue = ((Double)value).toString();
        } else if(value instanceof Boolean) {
            strValue = ((Boolean)value).toString();
        } else if(value instanceof Date) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            strValue = format.format((Date)value);
        } else {
            strValue = value.toString();
        }

        return this.put(key, strValue);
    }

    public String put(String key, String value) {
        return StringUtils.areNotEmpty(new String[]{key, value})?(String)super.put(key, value):null;
    }
}
