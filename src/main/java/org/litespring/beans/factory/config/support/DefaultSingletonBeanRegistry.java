package org.litespring.beans.factory.config.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		Assert.notNull(beanName, "'beanName' must not be null");

		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null) {
			throw new IllegalStateException("Could not register object [" + singletonObject + "] under bean name '"
					+ beanName + "': there is already object [" + oldObject + "] bound");
		}
		singletonObjects.put(beanName, singletonObject);

	}

}
