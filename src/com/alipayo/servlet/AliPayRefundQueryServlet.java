package com.alipayo.servlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipayo.util.AliPayClientServer;
import com.alipayo.util.configUtil.AliPayOuterConfig;
import com.alipayo.util.configUtil.DES;
import com.alipayo.util.configUtil.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: ZhaoTao
 * \* Date: 2017/3/28
 * \* Time: 9:57
 * \* Description:
 * \
 */
public class AliPayRefundQueryServlet  extends HttpServlet {
    //xml中的配置
    List<AliPayOuterConfig> configList = XMLReader.loadconfiglist();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AlipayClient alipayClient = AliPayClientServer.Client();

        String parm =  new String((request.getParameter("parm")).getBytes("ISO-8859-1"),"UTF-8");
        String mixKey = new String((request.getParameter("mixKey")).getBytes("ISO-8859-1"),"UTF-8");

        String out_trade_no = "";
        String out_request_no = "";
        String parmTemp = null;  //解密
        try {
            parmTemp = DES.jiemi(parm,mixKey);
            String parms[] =  parmTemp.split("\\|");
            out_trade_no = parms[0];
            out_request_no = parms[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlipayTradeFastpayRefundQueryRequest queryRequest = new AlipayTradeFastpayRefundQueryRequest();
        queryRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"out_request_no\":\""+out_request_no+"\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse queryResponse = null;
        try {
            queryResponse = alipayClient.execute(queryRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        String returnMsg = null;
        PrintWriter out = response.getWriter();
        if(queryResponse.isSuccess()){
            returnMsg = "10000".equals(queryResponse.getCode()) && !StringUtils.isEmpty(queryResponse.getRefundAmount())?"success":"error";
        } else {
            returnMsg = "error";
        }
        out.print(returnMsg);
    }
}
