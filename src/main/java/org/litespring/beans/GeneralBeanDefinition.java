package org.litespring.beans;

public class GeneralBeanDefinition implements BeanDefinition {
	
	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = BeanDefinition.SCOPE_DEFAULT;

	public GeneralBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
		return beanClassName;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}

	@Override
	public boolean isPrototype() {
		return prototype;
	}

	@Override
	public String getScope() {
		return scope;
	}

	@Override
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = this.scope.equals(SCOPE_SINGLETON) || this.scope.equals(SCOPE_DEFAULT);
		this.prototype = this.scope.equals(SCOPE_PROTOTYPE);
	}

}
