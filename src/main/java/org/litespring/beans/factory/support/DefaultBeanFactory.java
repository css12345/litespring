package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.support.DefaultSingletonBeanRegistry;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements ConfigurableBeanFactory, BeanDefinationRegistry {

	// key为bean标签的id,value为beanDefinition
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	private ClassLoader classLoader;

	public DefaultBeanFactory() {

	}

	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return this.beanDefinitionMap.get(beanId);
	}

	@Override
	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
		if (beanDefinition == null)
			throw new BeanCreationException("Bean Definition does not exist");

		String beanClassName = beanDefinition.getBeanClassName();
		if (beanDefinition.isSingleton()) {
			Object bean = this.getSingleton(beanClassName);
			if (bean == null) {
				bean = createBean(beanClassName);
				this.registerSingleton(beanClassName, bean);
			}
			return bean;
		}

		return createBean(beanClassName);
	}

	private Object createBean(String beanClassName) {
		ClassLoader classloader = this.getBeanClassLoader();
		
		// 通过反射创建实例对象
		try {
			Class<?> clazz = classloader.loadClass(beanClassName);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
		}
	}

	@Override
	public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanId, beanDefinition);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return (classLoader == null) ? ClassUtils.getDefaultClassLoader() : classLoader;
	}

}
