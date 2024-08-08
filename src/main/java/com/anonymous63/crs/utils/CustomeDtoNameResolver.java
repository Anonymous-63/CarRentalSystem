package com.anonymous63.crs.utils;

public class CustomeDtoNameResolver {

    private CustomeDtoNameResolver() {
        throw new IllegalStateException("Utility class");
    }

    public static String resolve(Class<?> clazz) {
        if (clazz.isAnnotationPresent(CustomDtoName.class)) {
            return clazz.getAnnotation(CustomDtoName.class).value(); // get value from annotation
        }
        return clazz.getSimpleName(); // default to class name
    }
}
