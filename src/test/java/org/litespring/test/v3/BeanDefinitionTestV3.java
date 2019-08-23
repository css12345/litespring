package org.litespring.test.v3;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.config.ConstructorArgument;
import org.litespring.beans.factory.config.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class BeanDefinitionTestV3 {

	@Test
	public void testGetBeanDefinition() {
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
		Resource resource = new ClassPathResource("petstore-v3.xml");
		reader.loadBeanDefinition(resource);
		
		BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition("petStore");
		ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
		List<ValueHolder> argumentValues = constructorArgument.getArgumentValues();
		
		assertEquals(3, argumentValues.size());
		
		RuntimeBeanReference ref1 = (RuntimeBeanReference) argumentValues.get(0).getValue();
		assertEquals("accountDao", ref1.getBeanName());
		
		RuntimeBeanReference ref2 = (RuntimeBeanReference) argumentValues.get(1).getValue();
		assertEquals("itemDao", ref2.getBeanName());
		
		TypedStringValue strValue = (TypedStringValue) argumentValues.get(2).getValue();
		assertEquals("1", strValue.getValue());
	}

}
