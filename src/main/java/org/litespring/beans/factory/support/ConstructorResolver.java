package org.litespring.beans.factory.support;

import java.lang.reflect.Constructor;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.ConstructorArgument;
import org.litespring.util.MethodInvoker;

public class ConstructorResolver {

	private ConfigurableBeanFactory beanFactory;

	public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object autowireConstructor(BeanDefinition beanDefinition) {
		Constructor<?> constructorToUse = null;
		Object[] argsToUse = null;
		Class<?> beanClass;
		try {
			beanClass = this.beanFactory.getBeanClassLoader().loadClass(beanDefinition.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreationException(beanDefinition.getID(), "Instantiation of bean failed, can't resolve class",
					e);
		}

		ConstructorArgument cargs = beanDefinition.getConstructorArgument();
		int minNrOfArgs = resolveConstructorArguments(beanDefinition, cargs);

		Constructor<?>[] candidates = beanClass.getDeclaredConstructors();
		AutowireUtils.sortConstructors(candidates);
		int minTypeDiffWeight = Integer.MAX_VALUE;
		for (Constructor<?> candidate : candidates) {
			Class<?>[] paramTypes = candidate.getParameterTypes();

			if (constructorToUse != null && argsToUse.length > paramTypes.length) {
				// Already found greedy constructor that can be satisfied ->
				// do not look any further, there are only less greedy constructors left.
				break;
			}
			if (paramTypes.length < minNrOfArgs) {
				continue;
			}

			ArgumentsHolder argsHolder = createArgumentArray(beanDefinition, cargs, new SimpleTypeConverter(),
					paramTypes);
			int typeDiffWeight = argsHolder.getTypeDifferenceWeight(paramTypes);
			// Choose this constructor if it represents the closest match.
			if (typeDiffWeight < minTypeDiffWeight) {
				constructorToUse = candidate;
				argsToUse = argsHolder.arguments;
				minTypeDiffWeight = typeDiffWeight;
			}
		}

		if (constructorToUse == null) {
			throw new BeanCreationException(beanDefinition.getID(), "can't find a apporiate constructor");
		}

		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreationException(beanDefinition.getID(),
					"can't find a create instance using " + constructorToUse);
		}
	}

	/**
	 * Create an array of arguments to invoke a constructor method, given the
	 * resolved constructor argument values.
	 */
	private ArgumentsHolder createArgumentArray(BeanDefinition mbd, ConstructorArgument resolvedValues,
			TypeConverter converter, Class<?>[] paramTypes) {

		ArgumentsHolder args = new ArgumentsHolder(paramTypes.length);

		for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
			Class<?> paramType = paramTypes[paramIndex];

			ConstructorArgument.ValueHolder valueHolder = resolvedValues.getArgumentValue(paramIndex);

			Object originalValue = valueHolder.getResolvedValue();
			Object convertedValue = converter.convertIfNecessary(originalValue, paramType);

			args.arguments[paramIndex] = convertedValue;
			args.rawArguments[paramIndex] = originalValue;
		}

		return args;
	}

	/**
	 * Resolve the constructor arguments for this bean into the resolvedValues
	 * object. This may involve looking up other beans.
	 * <p>
	 * This method is also used for handling invocations of static factory methods.
	 */
	private int resolveConstructorArguments(BeanDefinition mbd, ConstructorArgument cargs) {

		BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);

		int minNrOfArgs = cargs.getArgumentCount();

		for (ConstructorArgument.ValueHolder valueHolder : cargs.getArgumentValues()) {
			Object resolvedValue = valueResolver.resolveValueIfNecessary(valueHolder.getValue());
			valueHolder.setResolvedValue(resolvedValue);
		}

		return minNrOfArgs;
	}

	private static class ArgumentsHolder {

		public final Object rawArguments[];

		public final Object arguments[];

		public ArgumentsHolder(int size) {
			this.rawArguments = new Object[size];
			this.arguments = new Object[size];
		}

		public int getTypeDifferenceWeight(Class<?>[] paramTypes) {
			// If valid arguments found, determine type difference weight.
			// Try type difference weight on both the converted arguments and
			// the raw arguments. If the raw weight is better, use it.
			// Decrease raw weight by 1024 to prefer it over equal converted weight.
			int typeDiffWeight = MethodInvoker.getTypeDifferenceWeight(paramTypes, this.arguments);
			int rawTypeDiffWeight = MethodInvoker.getTypeDifferenceWeight(paramTypes, this.rawArguments) - 1024;
			return (rawTypeDiffWeight < typeDiffWeight ? rawTypeDiffWeight : typeDiffWeight);
		}

	}

}
