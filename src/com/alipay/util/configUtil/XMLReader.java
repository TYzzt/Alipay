package com.alipay.util.configUtil;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZhaoTao on 2016/2/29.
 */
public class XMLReader {
    private static String filename = "aliPayOuterConfig.xml";
    private static List<AliPayOuterConfig> configList;

    /**
     * 从配置文件中读取参数并保存到Config类中,
     * 很多时候程序中会多次使用到配置中的参数,
     * 于是设置成静态方法,读取一次后就一直保存其中的参数，
     * 不再反复读取
     *
     * @return
     */

//    public static AliPayOuterConfig loadconfig() {
//        if (config == null)
//            config = getConfig();
//        return config;
//    }

    public static List<AliPayOuterConfig> loadconfiglist() {
        if (configList == null)
            configList = getConfig();
        return configList;
    }

    private static List<AliPayOuterConfig> getConfig() {
        AliPayOuterConfig cf;
        List<AliPayOuterConfig> list = new ArrayList<>();

        try{
            filename = AlipayConfig.class.getResource("") + filename;
            filename = filename.substring(6);
            File f = new File(filename);
            if (!f.exists()) {
                //AlipayCore.logResult("Error : Config file doesn't exist!");
                System.out.println("error");
                System.exit(1);
            }
            SAXReader reader = new SAXReader();
            Document doc;
            doc = reader.read(f);
            Element root = doc.getRootElement();
            Element data;
            Iterator<?> itr = root.elementIterator("ServerMsg");
            while(itr.hasNext()){
                data = (Element) itr.next();
                cf = new AliPayOuterConfig();
                cf.setNAME(data.elementText("NAME").trim());
                cf.setTRADE_PENDING_URL(data.elementText("TRADE_PENDING_URL").trim());
                cf.setTRADE_SUCCESS_URL(data.elementText("TRADE_SUCCESS_URL").trim());
                cf.setREFUND_SUCCESS_PART_URL(data.elementText("REFUND_SUCCESS_PART_URL").trim());
                cf.setREFUND_SUCCESS_FULL_URL(data.elementText("REFUND_SUCCESS_FULL_URL").trim());
                cf.setTRADE_CLOSED_URL(data.elementText("TRADE_CLOSED_URL").trim());
                cf.setTRADE_FINISHED_URL(data.elementText("TRADE_FINISHED_URL").trim());
                cf.setWAIT_BUYER_PAY_URL(data.elementText("WAIT_BUYER_PAY_URL").trim());

                list.add(cf);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            AlipayCore.logResult("Error : " + ex.toString());
        }
        return list;
    }
    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(XMLReader.loadconfiglist().get(1).getNAME());
    }
}
