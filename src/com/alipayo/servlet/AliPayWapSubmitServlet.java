package com.alipayo.servlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipayo.config.AlipayConfig;
import com.alipayo.util.AliPayClientServer;
import com.alipayo.util.configUtil.AliPayOuterConfig;
import com.alipayo.util.configUtil.DES;
import com.alipayo.util.configUtil.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: ZhaoTao
 * \* Date: 2017/2/13
 * \* Time: 9:11
 * \* Description:
 * \
 */
public class AliPayWapSubmitServlet extends HttpServlet {

    //xml中的配置
    List<AliPayOuterConfig> configList = XMLReader.loadconfiglist();

    //服务器异步通知页面路径
    String notify_url = configList.get(0).getALIPAY_BASIC_URL()+ AlipayConfig.wap_notify_url;

    String return_url;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = AliPayClientServer.Client();
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        String parm =  new String((request.getParameter("parm")).getBytes("ISO-8859-1"),"UTF-8");
        String mixKey = new String((request.getParameter("mixKey")).getBytes("ISO-8859-1"),"UTF-8");

        String out_trade_no = null;
        String subject = null;
        String total_fee = null;
        try {
            String parmTemp = DES.jiemi(parm,mixKey);  //解密
            String parms[] =  parmTemp.split("\\|");
            out_trade_no = new String(parms[0].getBytes(),"UTF-8");
            subject  = parms[1];

            total_fee =new String( parms[2].getBytes(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(AliPayOuterConfig config:configList){
            if(out_trade_no.startsWith(config.getNAME())){
                return_url = config.getRETURN_URL();
                break;
            }
        }

        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"total_amount\":\""+total_fee+"\"," +
                "    \"subject\":\""+subject+"\"," +
                "    \"seller_id\":\""+AlipayConfig.partner+"\"," +
                "    \"product_code\":\"QUICK_WAP_PAY\"" +
                "  }");//填充业务参数

        String form = null; //调用SDK生成表单
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + "UTF-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();

    }
}
