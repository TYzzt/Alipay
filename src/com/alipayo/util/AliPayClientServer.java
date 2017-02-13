package com.alipayo.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipayo.config.AlipayConfig;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: ZhaoTao
 * \* Date: 2017/2/9
 * \* Time: 14:02
 * \* Description: 支付宝客户端服务-2017年2月9日
 * \
 */
public class AliPayClientServer {
    private static AlipayClient alipayClientApp;

    private static AlipayClient  newServer(){
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.appId,
                AlipayConfig.keyRSA1,
                "json",
                "UTF-8",
                AlipayConfig.keyPublic,
                "RSA");
        return alipayClient;
    }

    public static AlipayClient Client(){
        if (null == alipayClientApp) {
            alipayClientApp = newServer();
        }
        return alipayClientApp;
    }
}
