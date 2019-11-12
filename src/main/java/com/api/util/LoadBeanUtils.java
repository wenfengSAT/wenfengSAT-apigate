package com.api.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description： 动态加载Bean至Spring容器工具类
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:41:36]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
public class LoadBeanUtils implements ApplicationContextAware {

	private static ConfigurableApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = (ConfigurableApplicationContext) context;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		}
		return applicationContext;
	}

	/**
	 * 主动向Spring容器中注册bean
	 *
	 * @param applicationContext
	 *            Spring容器
	 * @param name
	 *            BeanName
	 * @param clazz
	 *            注册的bean的类性
	 * @param args
	 *            构造方法的必要参数，顺序和类型要求和clazz中定义的一致
	 * @param <T>
	 * @return 返回注册到容器中的bean对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T registerBean(String name, Class<T> clazz, Object... args) {
		if (applicationContext.containsBean(name)) {
			Object bean = applicationContext.getBean(name);
			if (bean.getClass().isAssignableFrom(clazz)) {
				return (T) bean;
			} else {
				throw new RuntimeException("BeanName 重复 " + name);
			}
		}

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		for (Object arg : args) {
			beanDefinitionBuilder.addConstructorArgValue(arg);
		}
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

		BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
		beanFactory.registerBeanDefinition(name, beanDefinition);
		return applicationContext.getBean(name, clazz);
	}

	/**
	 * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) throws ClassNotFoundException {
		return getApplicationContext().getBean(name, clazz);
	}

	public static final Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	public static final Object getBean(String beanName, String className) throws ClassNotFoundException {
		Class<?> clz = Class.forName(className);
		return getApplicationContext().getBean(beanName, clz.getClass());
	}
}
