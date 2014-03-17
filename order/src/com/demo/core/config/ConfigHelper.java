package com.demo.core.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;

import com.demo.utils.ApplicationUtils;
import com.demo.utils.BeanUtils;
import com.demo.utils.JaxbBinder;

/**
 * 配置文件读写工具
 *
 */
public class ConfigHelper {
	
	private static SiteConfig _siteConfig = null;
	public static SiteConfig getSiteConfig() {
		if(_siteConfig == null){
			try {
				JaxbBinder binder = new JaxbBinder(SiteConfig.class);
				String xml =  FileUtils.readFileToString(new File(ConfigHelper.class.getResource(SiteConfig.PATH).getFile()), "UTF-8");
				_siteConfig = binder.fromXml(xml);
			} catch (IOException e) {
			}
			if(_siteConfig == null){
				_siteConfig = new SiteConfig();
			}
		}
		return _siteConfig;
	}
	
	public static void saveSiteConfig() {
		if(_siteConfig != null){
			try {
				JaxbBinder binder = new JaxbBinder(SiteConfig.class);
				OutputStream os = new FileOutputStream(ConfigHelper.class.getResource(SiteConfig.PATH).getFile());
				binder.toXml(_siteConfig, os);
				os.close();
			}
			catch (IOException e) {
			}
			saveSiteConfig2Application();
		}
	}
	
	public static void saveSiteConfig2Application(){
		SiteConfig siteConfig = getSiteConfig();
		Field[] fields = SiteConfig.class.getDeclaredFields();
		for(Field field : fields){
			try {
				ApplicationUtils.put(field.getName(), BeanUtils.forceGetProperty(siteConfig, field.getName()));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
}
