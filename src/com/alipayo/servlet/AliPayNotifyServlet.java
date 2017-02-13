package com.alipayo.servlet;

import com.alipayo.util.AlipayCore;
import com.alipayo.util.AlipayNotify;
import com.alipayo.util.configUtil.AliPayOuterConfig;
import com.alipayo.util.configUtil.DES;
import com.alipayo.util.configUtil.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoTao on 2016/2/23.
 */

/**
 * 支付宝通知处理
 */
/*@WebServlet(name="AliPayNotifyServlet",urlPatterns="/aliPayNotify")*/
public class AliPayNotifyServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取支付宝POST过来反馈信息

        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //退款状态
        String refund_status = "";
        if(null!=request.getParameter("refund_status")){
            refund_status = new String(request.getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
        }
        System.out.println("notifyyyyyyyyyyyyy"+out_trade_no+trade_status);
        List<AliPayOuterConfig> configList = XMLReader.loadconfiglist();
        PrintWriter out = response.getWriter();
        if(AlipayNotify.verify(params)){//验证成功
            //判断订单号是哪个项目
            AliPayOuterConfig config = null;
            for(AliPayOuterConfig temp : configList){
               if(out_trade_no.startsWith(temp.getNAME())){
                   config = temp;
                   break;
               }
            }
            if(config == null){
                AlipayCore.logResult("订单成功且未处理:"+out_trade_no);
                out.println("success");
                out.flush();
                out.close();
                return;
            }
            /**
             * 下面通知情况根据公司业务自行调整
             */
            String u = ""; //交易成功后访问的url
            String parm = null;//url参数
            try {
                parm = "?out_trade_no="+ DES.encrypt(out_trade_no)+"&trade_status="+trade_status+"&trade_no="+trade_no;
            } catch (Exception e) {
                parm = "?out_trade_no="+ out_trade_no+"&trade_status="+trade_status+"&trade_no="+trade_no;
            }
            if(trade_status.equals("WAIT_BUYER_PAY")){  //交易创建，等待买家付款。
                u = config.getWAIT_BUYER_PAY_URL() + parm;
            } else if (trade_status.equals("TRADE_CLOSED")) {   //在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易。
                u = config.getTRADE_CLOSED_URL() + parm;
            } else if (trade_status.equals("TRADE_SUCCESS")) {   //交易成功，且可对该交易做操作，如：多级分润、退款等。
                u = config.getTRADE_SUCCESS_URL() + parm;
            } else if (trade_status.equals("TRADE_PENDING")) {   //等待卖家收款（买家付款后，如果卖家账号被冻结）。
                u = config.getTRADE_PENDING_URL() + parm;
            } else if (trade_status.equals("TRADE_CLOSED") && refund_status.equals("REFUND_SUCCESS")) {  //全额退款情况
                u = config.getTRADE_CLOSED_URL() + parm+"&refund_status=REFUND_SUCCESS";
            } else if (trade_status.equals("TRADE_SUCCESS") && refund_status.equals("REFUND_SUCCESS")) {  //非全额退款情况
                u = config.getTRADE_SUCCESS_URL() + parm+"&refund_status=REFUND_SUCCESS";
            } else if (trade_status.equals("TRADE_FINISHED")) {     //交易成功且结束，即不可再做任何操作。
                u = config.getTRADE_FINISHED_URL() + parm;
            }else{
                //未知的状态处理
            }
            String state = openUrl(u, null);
            System.out.println("state:"+state);
            if(state.equals("success")){
                out.println("success");
            }else{
                out.println("fail");
            }

            out.flush();
            out.close();
        }else{//验证失败
            out.println("fail");
            out.flush();
            out.close();
        }

    }


    public  String openUrl(String u,String json) throws IOException{
        URL url = new URL(u);    // 把字符串转换为URL请求地址
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接


        if(null!=json){
//        	connection.setRequestMethod("POST");
//        	connection.setRequestProperty("Content-Type", "application/x-javascript; charset="+ "UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();// 连接会话
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(json);
            osw.flush();
            osw.close();
        }else{
            connection.connect();// 连接会话
        }

        // 获取输入流
        int respCode = connection.getResponseCode();

        String temp =null;
        if (respCode == 200)
        {
            temp = ConvertStream2Json(connection.getInputStream());
        }
        connection.disconnect();// 断开连接
        return temp;
    }

    private String ConvertStream2Json(InputStream inputStream)
    {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return jsonStr;
    }
}



