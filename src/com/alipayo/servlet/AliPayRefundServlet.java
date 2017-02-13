package com.alipayo.servlet;

import com.alipayo.config.AlipayConfig;
import com.alipayo.util.AlipaySubmit;
import com.alipayo.util.configUtil.AliPayOuterConfig;
import com.alipayo.util.configUtil.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Buce on 2016/2/29.
 */

/**
 * 退款订单提交
 *
 * param WIDbatch_no     批次号                       必填
 * param WIDbatch_num    退款笔数                     必填,格式:  原付款支付宝交易号^退款总金额^退款理由；多笔交易用#号分割
 *                                                         eg: 2016030321001004730279794398^102.1^买错了#2016030321001004730279794100^10.1^哈哈
 * param WIDdetail_data  退款详细数据                 必填
 *
 */
/*@WebServlet(name="AliPayRefundServlet",urlPatterns="/aliPayRefund")*/
public class AliPayRefundServlet extends HttpServlet {
    //xml中的配置
    List<AliPayOuterConfig> configList = XMLReader.loadconfiglist();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ////////////////////////////////////请求参数//////////////////////////////////////

        //服务器异步通知页面路径
        String notify_url = configList.get(0).getALIPAY_BASIC_URL()+AlipayConfig.notify_url;
        //需http://格式的完整路径，不能加?id=123这类自定义参数
        //需http://格式的完整路径，不允许加?id=123这类自定义参数
        //必填
        //退款当天日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String refund_date = "";
        try {
            refund_date = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        //必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13

        //批次号
        String batch_no = new String(request.getParameter("WIDbatch_no").getBytes("ISO-8859-1"),"UTF-8");
        //必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001
        //退款笔数
        String batch_num = new String(request.getParameter("WIDbatch_num").getBytes("ISO-8859-1"),"UTF-8");
        //必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
        //退款详细数据
        String detail_data = new String(request.getParameter("WIDdetail_data").getBytes("ISO-8859-1"),"UTF-8");
        //必填，具体格式请参见接口技术文档 ####格式:  原付款支付宝交易号^退款总金额^退款理由；多笔交易用#号分割
        // eg: 2016030321001004730279794398^102.1^买错了#2016030321001004730279794100^10.1^哈哈

        //////////////////////////////////////////////////////////////////////////////////

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);

        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        System.out.println(sHtmlText);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
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
