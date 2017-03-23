package com.alipayo.servlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
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

/*@WebServlet(name="AliPayRefundServlet",urlPatterns="/aliPayRefund")*/
public class AliPayRefundServlet extends HttpServlet {
    //xml中的配置
    List<AliPayOuterConfig> configList = XMLReader.loadconfiglist();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AlipayClient alipayClient = AliPayClientServer.Client();

        String parm =  new String((request.getParameter("parm")).getBytes("ISO-8859-1"),"UTF-8");
        String mixKey = new String((request.getParameter("mixKey")).getBytes("ISO-8859-1"),"UTF-8");

        String out_trade_no = null;
        String refund_amount = null;
        String refund_reason = null;
        String out_request_no = null;
        String operator_id = null;

        String parmTemp = null;  //解密
        try {
            parmTemp = DES.jiemi(parm,mixKey);
            String parms[] =  parmTemp.split("\\|");
            out_trade_no = parms[0];
            refund_amount = parms[1];
            refund_reason = parms[2];
            out_request_no = parms[3];
            operator_id = parms[4];
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        refundRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"refund_amount\":"+refund_amount+"," +
                "    \"refund_reason\":\""+refund_reason+"\"," +
                "    \"out_request_no\":\""+out_request_no+"\"," +
                "    \"operator_id\":\""+operator_id+"\"" +
                "  }");
        AlipayTradeRefundResponse refundResponse = null;
        try {
            refundResponse = alipayClient.execute(refundRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        String returnMsg = null;
        PrintWriter out = response.getWriter();

        if(refundResponse.isSuccess()){
            //申请退款成功
            if("Success".equals(refundResponse.getMsg())){
                returnMsg = "success";
            }else{
                returnMsg = "error";
            }
        } else {
            returnMsg = "error";
        }
        out.print(returnMsg);
        out.flush();
        out.close();
    }
}
