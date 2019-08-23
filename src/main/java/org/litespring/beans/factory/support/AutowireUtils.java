package org.litespring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

abstract class AutowireUtils {

	/**
	 * Sort the given constructors, preferring public constructors and "greedy" ones with
	 * a maximum number of arguments. The result will contain public constructors first,
	 * with decreasing number of arguments, then non-public constructors, again with
	 * decreasing number of arguments.
	 * @param constructors the constructor array to sort
	 */
	public static void sortConstructors(Constructor<?>[] constructors) {
		Arrays.sort(constructors, new Comparator<Constructor<?>>() {
			@Override
			public int compare(Constructor<?> c1, Constructor<?> c2) {
				boolean p1 = Modifier.isPublic(c1.getModifiers());
				boolean p2 = Modifier.isPublic(c2.getModifiers());
				if (p1 != p2) {
					return (p1 ? -1 : 1);
				}
				int c1pl = c1.getParameterTypes().length;
				int c2pl = c2.getParameterTypes().length;
				return (c1pl < c2pl ? 1 : (c1pl > c2pl ? -1 : 0));
			}
		});
	}

}
