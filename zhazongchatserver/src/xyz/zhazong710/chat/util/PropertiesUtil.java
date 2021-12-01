package xyz.zhazong710.chat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//配置文件
public class PropertiesUtil {
	
	private static PropertiesUtil propertiesUtil = null;
	private Properties properties;
	
	//配置信息
	private PropertiesUtil() {
		
		properties = new Properties();
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
		
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//获取配置信息对象
	public static PropertiesUtil getPropertiesUtil() {
		
		if(propertiesUtil == null) {
			propertiesUtil = new PropertiesUtil();
		}
		
		return propertiesUtil;
		
	}
	
	//获取信息
	public String getValue(String key) {
		return properties.getProperty(key);
	}

}
