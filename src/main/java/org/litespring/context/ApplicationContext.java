package org.litespring.context;

import org.litespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * 引入接口的目的是使用户不用去关注底层的BeanFactory及XmlBeanDefinitionReader，<br>
 * 继承ConfigurableBeanFactory的原因是需要给用户提供配置classLoader方法
 * @author cs
 *
 */
public interface ApplicationContext extends ConfigurableBeanFactory {

}
