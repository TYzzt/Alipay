package com.alipay.servlet;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhaoTao on 2016/2/23.
 */

/**
 * 提交订单,返回支付宝的支付页面
 *
 * param WIDout_trade_no 商户网站订单系统中唯一订单号 必填
 * param WIDsubject      订单名称                     必填
 * param WIDtotal_fee    付款金额                     必填
 * param WIDbody         订单描述
 * param WIDshow_url     商品展示地址
 *
 */
@WebServlet(name="AliPaySubmitServlet",urlPatterns="/aliPaySubmit")
public class AliPaySubmitServlet extends HttpServlet{
    //支付类型
    String payment_type = "1";
    //必填，不能修改
    //服务器异步通知页面路径
    String notify_url = "http://zhaotaotest.tunnel.qydev.com/AliPay/aliPayNotify";
    //需http://格式的完整路径，不能加?id=123这类自定义参数

    //页面跳转同步通知页面路径
    String return_url = "http://zhaotaotest.tunnel.qydev.com/AliPay/aliPayReturn";
    //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //商户订单号
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //商户网站订单系统中唯一订单号，必填

        //订单名称
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //必填

        //付款金额
        String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
        //必填

        //订单描述

        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        //商品展示地址
        String show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
        //需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

        //防钓鱼时间戳
        String anti_phishing_key = "";
        //若要使用请调用类文件submit中的query_timestamp函数

        //客户端的IP地址
        String exter_invoke_ip = "";
        //非局域网的外网IP地址，如：221.0.0.1


        //////////////////////////////////////////////////////////////////////////////////

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML>");
        out.println("<HTML>");
        out.println(" <HEAD>");
        out.println("<TITLE>A Servlet</TITLE>");
        out.println("<meta http-equiv=\"content-type\" " + "content=\"text/html; charset=utf-8\">");
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println(sHtmlText);
        out.println(" </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

}