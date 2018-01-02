package com.hisun.lemon.cpi.alipay.ebankpay;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hisun.channel.client.IChannelClient;
import com.hisun.channel.data.Request;
import com.hisun.channel.data.Response;
import com.hisun.lemon.cpi.alipay.ebankpay.AliPayEnumCommon.EnumSource;

/**
 * User : Rui Date : 2017/8/28 Time : 17:12
 **/
@Component
public class AlipayApi {

	@Resource
	private IChannelClient channelClient;

	@SuppressWarnings("unchecked")
	public <T> T doSend(Object obj, EnumSource source) {
		if (source == null) {
			return null;
		}
		Request request = new Request();
		request.setRoute("ALIPAY");
		request.setBusiType(source.name());
		request.setSource(source.name());
		request.setTarget(obj);
		request.setRequestId("1");
		Response response = channelClient.request(request);
		Object t =  response.getResult();
		return (T)t;
	}
}
