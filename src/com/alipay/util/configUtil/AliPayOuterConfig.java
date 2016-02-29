package com.alipay.util.configUtil;

/**
 * Created by ZhaoTao on 2016/2/29.
 */

/**
 * 返回配置
 */
public class AliPayOuterConfig {
   public String TRADE_FINISHED_URL;
   public String TRADE_SUCCESS_URL;

    public String getTRADE_FINISHED_URL() {
        return TRADE_FINISHED_URL;
    }

    public void setTRADE_FINISHED_URL(String TRADE_FINISHED_URL) {
        this.TRADE_FINISHED_URL = TRADE_FINISHED_URL;
    }

    public String getTRADE_SUCCESS_URL() {
        return TRADE_SUCCESS_URL;
    }

    public void setTRADE_SUCCESS_URL(String TRADE_SUCCESS_URL) {
        this.TRADE_SUCCESS_URL = TRADE_SUCCESS_URL;
    }
}
