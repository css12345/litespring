package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.GeneralBeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.core.io.Resource;

/**
 * 将读取配置文件并解析的职责从BeanFactory中分离出来
 * 
 * @author cs
 *
 */
public class XmlBeanDefinitionReader {
	private BeanDefinationRegistry beanDefinationRegistry;
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	private static final String SCOPE_ATTRIBUTE = "scope";
	
	public XmlBeanDefinitionReader(BeanDefinationRegistry beanDefinationRegistry) {
		this.beanDefinationRegistry = beanDefinationRegistry;
	}

	/**
	 * 使用dom4j获取配置文件中的bean标签，根据id和class创建BeanDefinition对象，然后调用beanDefinationRegistry注册到beanDefinitionMap中
	 * 
	 */
	public void loadBeanDefinition(Resource resource) {
		InputStream inputStream = null;

		SAXReader reader = new SAXReader();
		try {
			inputStream = resource.getInputStream();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement(); // <beans>
			Iterator<Element> iterator = root.elementIterator();

			while (iterator.hasNext()) {
				Element bean = iterator.next();
				String id = bean.attributeValue(ID_ATTRIBUTE);
				String beanClassName = bean.attributeValue(CLASS_ATTRIBUTE);
				
				BeanDefinition beanDefinition = new GeneralBeanDefinition(id, beanClassName);

				if(bean.attributeValue(SCOPE_ATTRIBUTE) != null) {
					beanDefinition.setScope(bean.attributeValue(SCOPE_ATTRIBUTE));			
				}
				
				this.beanDefinationRegistry.registerBeanDefinition(id, beanDefinition);
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException("parse resource " + resource.getDescription() + " failed", e);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
