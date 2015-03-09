package org.eps.common.util;

import org.springframework.core.GenericTypeResolver;

@SuppressWarnings("rawtypes")
public class GenericTypeResolverUtils {

	public static Class resolveTypeArgument(Class clazz, Class genericIfc) {
		return GenericTypeResolver.resolveTypeArguments(clazz, genericIfc)[0];
	}

}
