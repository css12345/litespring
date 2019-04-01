package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

	private DefaultBeanFactory beanFactory;
	private ClassLoader classLoader;
	
	public AbstractApplicationContext(String filePath, ClassLoader classLoader) {
		beanFactory = new DefaultBeanFactory();
		this.setBeanClassLoader(classLoader);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = getResourceByPath(filePath);
		reader.loadBeanDefinition(resource);
	}
	
	public AbstractApplicationContext(String filePath) {
		this(filePath, null);
	}
	
	protected abstract Resource getResourceByPath(String filePath);
	
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		this.beanFactory.setBeanClassLoader(classLoader);
	}
	
	@Override
	public Object getBean(String beanId) {
		return beanFactory.getBean(beanId);
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return (classLoader == null) ? ClassUtils.getDefaultClassLoader() : classLoader;
	}

}
