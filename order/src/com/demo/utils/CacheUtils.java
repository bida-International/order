package com.demo.utils;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ͨ�û������
 *
 */
public class CacheUtils {
	private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	private static Object lockObject = new Object();
	private static CacheUtils instance = null;
	public static CacheManager cacheManager = null;
	
	public static final String CACHE_NAME = "CacheUtilss";

	private CacheUtils() {
	}

	/**
	 * ��ȡ������ʵ��
	 * 
	 * @return LForumCacheʵ��
	 */
	public static CacheUtils getInstance() {
		if (instance == null) {
			synchronized (lockObject) {
				logger.info("�����µĻ�����ʵ��");
				instance = new CacheUtils();
			}
		}
		return instance;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return ���������
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * ��ȡ��̳����
	 * 
	 * @return ����
	 */
	public Cache getCache() {
		return getCacheManager().getCache(CACHE_NAME);
	}

	/**
	 * ��ӻ������
	 * 
	 * @param key ����KEY
	 * @param cacheObj �������
	 */
	public void addCache(String key, Object cacheObj) {
		getCache().put(new Element(key, cacheObj));
		if (logger.isDebugEnabled()) {
			logger.debug("���KEYΪ{}�Ļ������,��ǰ����{}��", key, getCache().getSize());
		}
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param key ����KEY
	 * @return �������
	 */
	public Object getCache(String key) {
		Element element = getCache().get(key);
		if (element == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("��ȡKEYΪ{}�Ļ������", key);
		}
		return (Object) element.getValue();

	}

	/**
	 * ��ȡָ�����ͻ������
	 * 
	 * @param <T> ָ������
	 * @param key  ����KEY
	 * @param classz ����Class
	 * @return ָ�����ͻ������
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCache(String key, Class<T> classz) {
		if (getCache(key) == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("��ȡKEYΪ{}������Ϊ{}�Ļ������", key, classz.getName());
		}
		return (T) getCache(key);
	}

	/**
	 * ��ȡָ������LIST����
	 * 
	 * @param <T>  ָ������
	 * @param key ����KEY
	 * @param classz ����Class
	 * @return ָ������LIST����
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListCache(String key, Class<T> classz) {
		if (getCache(key) == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("��ȡKEYΪ{}������Ϊ{}��LIST����", key, classz.getName());
		}
		return (List<T>) getCache(key);
	}

	/**
	 * �Ƴ�ָ��KEY�������
	 * 
	 * @param key ����KEY
	 */
	public void removeCache(String key) {
		if (getCache(key) != null) {
			getCache().remove(key);
			if (logger.isDebugEnabled()) {
				logger.debug("�Ƴ�ȡKEYΪ{}�Ļ������", key);
			}
		}
	}
}
