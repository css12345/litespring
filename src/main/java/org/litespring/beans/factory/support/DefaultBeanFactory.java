package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
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
				bean = createBean(beanDefinition);
				this.registerSingleton(beanClassName, bean);
			}
			return bean;
		}

		return createBean(beanDefinition);
	}

	private Object createBean(BeanDefinition beanDefinition) {
		// 创建实例
		Object bean = instantiateBean(beanDefinition);

		// 设置属性
		populateBean(beanDefinition, bean);
		return bean;
	}

	// 这里的bean相当于是petStoreService对象，
	// 注入解析后的对象是通过调用bean的setAccountDao(resolvedValue)
	// 和setItemDao(resolvedValue)方法
	private void populateBean(BeanDefinition beanDefinition, Object bean) {
		List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

		if (propertyValues == null || propertyValues.isEmpty())
			return;

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
		TypeConverter converter = new SimpleTypeConverter();
		try {
			for (PropertyValue propertyValue : propertyValues) {
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();
				Object convertedValue = null;
				if (propertyValue.isConverted())
					convertedValue = propertyValue.getConvertedValue();
				else {
					convertedValue = resolver.resolveValueIfNecessary(value);
					propertyValue.setConvertedValue(convertedValue);
				}

				// 将convertedValue的值设置到bean中
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
					if (propertyDescriptor.getName().equals(name)) {
						convertedValue = converter.convertIfNecessary(convertedValue,
								propertyDescriptor.getPropertyType());
						propertyDescriptor.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
			}
		} catch (Exception e) {
			throw new BeanCreationException(
					"Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
		}
	}

	private Object instantiateBean(BeanDefinition beanDefinition) {
		if (beanDefinition.hasConstructorArgumentValues()) {
			ConstructorResolver constructorResolver = new ConstructorResolver(this);
			return constructorResolver.autowireConstructor(beanDefinition);
		} else {

			String beanClassName = beanDefinition.getBeanClassName();
			ClassLoader classloader = this.getBeanClassLoader();

			// 通过反射创建实例对象
			try {
				Class<?> clazz = classloader.loadClass(beanClassName);
				return clazz.newInstance();
			} catch (Exception e) {
				throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
			}
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
