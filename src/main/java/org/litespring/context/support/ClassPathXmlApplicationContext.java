package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * 作为ApplicationContext的一种实现，从classpath下加载xml进行解析注册BeanDefinition 
 * @author cs
 *
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext(String filePath) {
		super(filePath);
	}

	@Override
	protected Resource getResourceByPath(String filePath) {
		return new ClassPathResource(filePath,this.getBeanClassLoader());
	}
}
