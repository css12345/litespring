package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

	private DefaultBeanFactory beanFactory;
	private ClassLoader classLoader;
	
	public AbstractApplicationContext(String filePath) {
		beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = getResourceByPath(filePath);
		reader.loadBeanDefinition(resource);
		beanFactory.setBeanClassLoader(this.getBeanClassLoader());
	}
	
	protected abstract Resource getResourceByPath(String filePath);

	@Override
	public Object getBean(String beanId) {
		return beanFactory.getBean(beanId);
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
