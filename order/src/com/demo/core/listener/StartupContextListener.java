package com.demo.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.demo.core.config.ConfigHelper;
import com.demo.utils.ApiCacheUtils;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.CacheUtils;

/**
 * 服务启动时执行
 *
 */
public class StartupContextListener extends ContextLoaderListener {

	private static final Log log = LogFactory
			.getLog(StartupContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("initializing context...");
		}
		ServletContext context = event.getServletContext();
		super.contextInitialized(event);

		ApplicationUtils.setServletContext(context);
		ConfigHelper.saveSiteConfig2Application(); // 读取site-config.xml配置文件
		CacheUtils.cacheManager = (CacheManager) this.getCtxBean(
				context, "ehcacheManager"); 
		ApiCacheUtils.cacheManager = (CacheManager) this.getCtxBean(
				context, "ehcacheManager"); 
		setupContext(context);
	}
	
	
	public static void setupContext(ServletContext context) {
		if (log.isDebugEnabled()) {
			log.debug("Initialization complete [OK]");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		super.contextDestroyed(arg0);
		
	}
	
	private Object getCtxBean(ServletContext servletContext, String beanName) {
		return WebApplicationContextUtils.getWebApplicationContext(servletContext)
				.getBean(beanName);
	}
	
}
