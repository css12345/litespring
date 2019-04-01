package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

public class BeanDefinitionTestV2 {
	
	//测试getPropertyValues()方法
	@Test
	public void testGetBeanDefinition() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
		
		BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
		List<PropertyValue> list = beanDefinition.getPropertyValues();
		assertEquals(5, list.size());
		
		PropertyValue accountDao = this.getProperty(list,"accountDao");
		assertNotNull(accountDao);
		assertTrue(accountDao.getValue() instanceof RuntimeBeanReference);
		
		PropertyValue itemDao = this.getProperty(list,"itemDao");
		assertNotNull(itemDao);
		assertTrue(itemDao.getValue() instanceof RuntimeBeanReference);
	}

	private PropertyValue getProperty(List<PropertyValue> list, String name) {
		for(PropertyValue propertyValue:list)
			if(propertyValue.getName().equals(name))
				return propertyValue;
		return null;
	}
}
