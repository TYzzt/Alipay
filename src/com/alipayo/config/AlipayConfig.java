package com.alipayo.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipayo.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "1";

	public static String appId = "1";

	// 收款支付宝账号，一般情况下收款账号就是签约账号
	public static String seller_email = "htyun@greatchn.com.cn";
	// 商户的私钥 MD5
	public static String key = "1";
	// 商户的私钥 RSA2
	public static String keyRSA1 = "1/uXKE+0GFUomG9d5vq3uL2pFVBVduM/LI6CH7OmnphLASKSTbVaumW0rZ3bQnObmxTmPhlCI9L/wmztw0KEDPqT+1oj3Od7vjqTqsbOCTLNs/hqkNMJVy+Mhjiw1FiFCEpci+AXJDv+bPM8dPDm79IHKyhlOUnwjdFiDvoMriwPu5shJ5V6B/lNyOusOwlqtp5TXi07oHrZYQrWfmIiHYbb+DD2xd1TThMvMGL5GD8Dfnnak5Mb07pRD8VytV+z94S/XqjgzH9/rvcRTgMW1hWOq5iWg3AgMBAAECggEAZjBjgi/QnBJ+x/jvyVV/TOC36cSAFc4zlOs6CPKlZQyoikXDQ6YfQceXFeV6RyKqL7tmRMKgX9b2Bn25WcWRp0XJFz1piiWoknAE4WSyF/mrcQkDc0oqX6Ak2HYNijOzciFHgxT/u9unT1Z6ut76UbQzdrMYcjdyTrL2O/PcXuPczQym/yJf9ZllVmAFfNsRlvEit0TsJSXg6uY2ePgwXtIWfozrNEe9syDHWdLxnAJPGoy/t5UY1hbfHT9dt+fCrc0GfyejSoc2rc0G7am1BBT2QuKk+43FU75WG9izZ+S0/w7t+L6+DAhlssJjGcgB7MSGH21t4+na89zFCbQIoQKBgQDiKWU7oNzwtKFBuvQcGZG6i5d8TRidsZjCKf68SxNaBTyMzMjV8NILSG0b4XfGYM5RgN4BG4jBZ3b0a61gDU31g84DxlGbzVzAQ58B9BSMsVVmxIg5HI/HVC7+ECpBi0pI9RaQ4+CixBINBm3d/TI/8k9AS+RxJEAaor83wOOWOQKBgQDHW1b1nBhdp0jsuWt4t6jwTAiFYW4l5f4cMpZRrpBivN1WzZ3JA8UG+N83CVgZEpJ4a1wviItlkI4MTZn1NhD5DnOf8TWb6ANE7Au+OiLU6sk5ff7W9EGLyDA27pqvlsxfBExo6XQojLJ6r/cSLT10BIoT8vPPGBvTIVLiJclx7wKBgE8QR6qr1vFzRDiVYgd8zNxZNpEm5qciUhP22G39FPvQftV+NevM77EwFay8isHYT+hgU7wxYwKx5/0Us5yzqgBdnqTlLNx506YHPcxSBukUiZWOuUy19nGptW/uOMeHKWYOAGCQuZRNVT4mdDw56to+HISOw4LmOF7XbF/SV/3JAoGARiC9cFzevWMxZgS755VD2nQEs1bYtoA3aZfqBQXRSMqDWhuum52124QEkYm58ef0mmO55Aa2cp3tOiq6M+Y3BkWtKuBhKVzueXpHjz1IL3oAGzdVRCxcOl3LiCdbRtVXQz1x253z/KexyTLJSR2c9oj6QZY/aeRjNVwNmfwM5wUCgYEAxxsxGow2oI3dw7lI6W1A6UESm4ImZkPAWmREW7LCajDbQmWuA/LOqqvP2hRsSpJLXg7ZtHenfNtNIOH0nA9I9MTOCX9FDbmhD32CmnOt9/VMlQJJW4Cu0AzwdqUp+SH5ke6xzfNAY2dJAebdnQMMoPsLRUHYCVHZYc0Ah6l5IkM=";
	//支付宝公钥
	public static String keyPublic = "1/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//DES加密KEY
	public static final String PASSWORD_CRYPT_KEY = "1";
	//服务器异步通知页面路径
	public static final String notify_url ="aliPayNotify";

	//服务器异步通知页面路径
	public static final String wap_notify_url ="aliPayWapNotify";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "MD5";

}
