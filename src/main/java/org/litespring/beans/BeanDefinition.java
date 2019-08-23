package org.litespring.beans;

import java.util.List;

import org.litespring.beans.factory.config.ConstructorArgument;

public interface BeanDefinition {

	String SCOPE_DEFAULT = "";
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";

	String getBeanClassName();

	boolean isSingleton();

	boolean isPrototype();

	String getScope();
	
	void setScope(String scope);

	List<PropertyValue> getPropertyValues();

	ConstructorArgument getConstructorArgument();

	String getID();

	boolean hasConstructorArgumentValues();
}
