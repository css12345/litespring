package org.litespring.beans.factory.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.GeneralBeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory implements BeanFactory {
	
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	
	//key为bean标签的id,value为beanDefinition
	HashMap<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
	

	public DefaultBeanFactory(String configureFile) {
		loadBeanDefinition(configureFile);
	}

	/**
	 *  使用dom4j获取配置文件中的bean标签，根据Id和class创建BeanDefinition对象，然后加入到beanDefinitionMap中
	 * @param configureFile
	 */
	public void loadBeanDefinition(String configureFile) {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(configureFile);
		
		
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(inputStream);
			Element root = document.getRootElement(); //<beans>
			Iterator<Element> iterator = root.elementIterator();
			
			while(iterator.hasNext()) {
				Element bean = iterator.next();
				String id = bean.attributeValue(ID_ATTRIBUTE);
				String beanClassName = bean.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition beanDefinition = new GeneralBeanDefinition(id,beanClassName);
				
				this.beanDefinitionMap.put(id, beanDefinition);
			}
		} catch (DocumentException e) {
			throw new BeanDefinitionStoreException("parse file " + configureFile + " failed", e);
		} finally {
			try {
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return this.beanDefinitionMap.get(beanId);
	}

	@Override
	public Object getBean(String beanId) {
		BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
		if(beanDefinition == null)
			throw new BeanCreationException("Bean Definition does not exist");
		
		ClassLoader classloader = ClassUtils.getDefaultClassLoader();
		String beanClassName = beanDefinition.getBeanClassName();
		
		//通过反射创建实例对象
		try {
			Class<?> clazz = classloader.loadClass(beanClassName);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed",e);
		}
	}

}
