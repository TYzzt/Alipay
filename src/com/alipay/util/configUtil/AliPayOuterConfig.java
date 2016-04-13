package com.alipay.util.configUtil;

/**
 * Created by ZhaoTao on 2016/2/29.
 */

/**
 * 返回配置
 */
public class AliPayOuterConfig {
//    订单标识
private String NAME;
//    交易完成时访问
private String TRADE_FINISHED_URL;
//    交易成功时访问
private String TRADE_SUCCESS_URL;
//    交易创建时访问
private String WAIT_BUYER_PAY_URL;
//    交易关闭时访问
private String TRADE_CLOSED_URL;
//    等待卖家收款时访问
private String TRADE_PENDING_URL;
//    全额退款时访问
private String REFUND_SUCCESS_FULL_URL;
//    部分退款时访问
private String REFUND_SUCCESS_PART_URL;
//    页面跳转同步通知页面路径
private String RETURN_URL;
    public String getRETURN_URL() {
        return RETURN_URL;
    }

    public void setRETURN_URL(String RETURN_URL) {
        this.RETURN_URL = RETURN_URL;
    }

    public String getWAIT_BUYER_PAY_URL() {
        return WAIT_BUYER_PAY_URL;
    }

    public void setWAIT_BUYER_PAY_URL(String WAIT_BUYER_PAY_URL) {
        this.WAIT_BUYER_PAY_URL = WAIT_BUYER_PAY_URL;
    }

    public String getTRADE_PENDING_URL() {
        return TRADE_PENDING_URL;
    }

    public void setTRADE_PENDING_URL(String TRADE_PENDING_URL) {
        this.TRADE_PENDING_URL = TRADE_PENDING_URL;
    }

    public String getTRADE_CLOSED_URL() {
        return TRADE_CLOSED_URL;
    }

    public void setTRADE_CLOSED_URL(String TRADE_CLOSED_URL) {
        this.TRADE_CLOSED_URL = TRADE_CLOSED_URL;
    }

    public String getREFUND_SUCCESS_FULL_URL() {
        return REFUND_SUCCESS_FULL_URL;
    }

    public void setREFUND_SUCCESS_FULL_URL(String REFUND_SUCCESS_FULL_URL) {
        this.REFUND_SUCCESS_FULL_URL = REFUND_SUCCESS_FULL_URL;
    }

    public String getREFUND_SUCCESS_PART_URL() {
        return REFUND_SUCCESS_PART_URL;
    }

    public void setREFUND_SUCCESS_PART_URL(String REFUND_SUCCESS_PART_URL) {
        this.REFUND_SUCCESS_PART_URL = REFUND_SUCCESS_PART_URL;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

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
