package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.GeneralBeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

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
	public static final String SCOPE_ATTRIBUTE = "scope";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String VALUE_ATTRIBUTE = "value";
	public static final String REF_ATTRIBUTE = "ref";
	public static final String PROPERTY_ATTRIBUTE = "property";

	protected final Log logger = LogFactory.getLog(getClass());

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

				if (bean.attributeValue(SCOPE_ATTRIBUTE) != null) {
					beanDefinition.setScope(bean.attributeValue(SCOPE_ATTRIBUTE));
				}

				parsePropertyElement(bean, beanDefinition);

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

	/**
	 * 获取带有property标签的元素列表，针对每个元素，如果name不空，进行解析创建PropertyValue对象加入到beanDefinition的propertyValues列表中
	 * @param bean 比如petstore-v2.xml中的bean标签
	 * @param beanDefinition 对当前bean标签对应类的一个描述，比如对PetStoreService的描述
	 */
	public void parsePropertyElement(Element bean, BeanDefinition beanDefinition) {
		Iterator<Element> iterator = bean.elementIterator(PROPERTY_ATTRIBUTE);
		while (iterator.hasNext()) {
			Element element = iterator.next();

			String name = element.attributeValue(NAME_ATTRIBUTE);
			if (!StringUtils.hasLength(name)) {
				logger.fatal("Tag 'property' must have a 'name' attribute");
				return;
			}

			Object value = parsePropertyValue(element, beanDefinition, name);
			PropertyValue propertyValue = new PropertyValue(name, value);
			beanDefinition.getPropertyValues().add(propertyValue);

		}
	}

	/**
	 * 从ele中取出ref属性和value属性，如果有ref属性，根据ref属性值创建RuntimeBeanReference对象并返回；
	 * 如果有value属性，根据value属性值创建TypedStringValue对象并返回；
	 * 否则会抛出异常信息
	 * @param ele property元素，如property name="accountDao" ref="accountDao"
	 * @param beanDefinition 当前的bean定义，比如petstore-v2.xml中的petStoreService的定义
	 * @param propertyName 在异常信息输出时用到
	 * @return
	 */
	public Object parsePropertyValue(Element ele, BeanDefinition beanDefinition, String propertyName) {
		String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'"
				: "<constructor-arg> element";

		boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
		boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		} else if (hasValueAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
			return valueHolder;
		} else
			throw new RuntimeException(elementName + " must specify a ref or value");
	}

}
