package com.alipay.util.configUtil;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by ZhaoTao on 2016/2/29.
 */
public class XMLReader {
    private static String filename = "aliPayOuterConfig.xml";
    private static AliPayOuterConfig config;

    /**
     * 从配置文件中读取参数并保存到Config类中,
     * 很多时候程序中会多次使用到配置中的参数,
     * 于是设置成静态方法,读取一次后就一直保存其中的参数，
     * 不再反复读取
     *
     * @return
     */

    public static AliPayOuterConfig loadconfig() {
        if (config == null)
            config = getConfig();
        return config;
    }

    private static AliPayOuterConfig getConfig() {
        AliPayOuterConfig cf = new AliPayOuterConfig();
        try{
            filename = AlipayConfig.class.getResource("") + filename;
            filename = filename.substring(6);
            File f = new File(filename);
            if (!f.exists()) {
                //AlipayCore.logResult("Error : Config file doesn't exist!");
                System.out.println("error111");
                System.exit(1);
            }
            SAXReader reader = new SAXReader();
            Document doc;
            doc = reader.read(f);
            Element root = doc.getRootElement();
            Element data;
            Iterator<?> itr = root.elementIterator("ServerMsg");
            data = (Element) itr.next();

            cf.setTRADE_FINISHED_URL(data.elementText("TRADE_FINISHED_URL").trim());
            cf.setTRADE_SUCCESS_URL(data.elementText("TRADE_SUCCESS_URL").trim());
        }catch (Exception ex){
            AlipayCore.logResult("Error : " + ex.toString());
        }
        return cf;
    }

    public static void main(String[] args) {
        XMLReader.getConfig();
    }
}
