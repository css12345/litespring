package org.litespring.beans.factory.config;

/**
 * 为了实现单例能力而定义的接口
 * @author cs
 *
 */
public interface SingletonBeanRegistry {
	Object getSingleton(String beanName);
	void registerSingleton(String beanName,Object singletonObject);
}
