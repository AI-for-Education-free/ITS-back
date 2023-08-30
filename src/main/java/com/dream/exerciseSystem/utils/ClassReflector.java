package com.dream.exerciseSystem.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassReflector {
  // Stolen from org.apache.commons.lang3.ClassUtils ;)
  private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

  static {
    primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
    primitiveWrapperMap.put(Byte.TYPE, Byte.class);
    primitiveWrapperMap.put(Character.TYPE, Character.class);
    primitiveWrapperMap.put(Short.TYPE, Short.class);
    primitiveWrapperMap.put(Integer.TYPE, Integer.class);
    primitiveWrapperMap.put(Long.TYPE, Long.class);
    primitiveWrapperMap.put(Double.TYPE, Double.class);
    primitiveWrapperMap.put(Float.TYPE, Float.class);
    primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
  }

  public static Field getFieldByName(String className, String fieldName) {
    try {
      var clazz = Class.forName(className);
      var field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field;
    } catch (ClassNotFoundException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  public static Field getFieldByNameAndType(String className, String fieldName, Class<?> expectedClazz) {
    var field = getFieldByName(className, fieldName);
    if (field.getType().isAssignableFrom(expectedClazz)) {
      return field;
    } else {
      throw new RuntimeException(String.format("Field %s has actual type %s, not expected type %s", fieldName, field.getType(), expectedClazz.getName()));
    }
  }

  public static Field getPrimitiveOrWrappedFieldByNameAndType(String className, String fieldName, Class<?> expectedClazz) {
    var field = getFieldByName(className, fieldName);
    if (expectedClazz.isPrimitive()) {
      expectedClazz = primitiveToWrapper(expectedClazz);
    }
    Class<?> actualClass = field.getType();
    if (actualClass.isPrimitive()) {
      actualClass = primitiveToWrapper(actualClass);
    }
    if (actualClass.isAssignableFrom(expectedClazz)) {
      return field;
    } else {
      throw new RuntimeException(String.format("Field %s has actual type %s, not expected type %s", fieldName, field.getType(), expectedClazz.getName()));
    }
  }

  public static List<Method> getMethodsByName(String className, String methodName) {
    Class<?> clazz;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    var methods = clazz.getDeclaredMethods();
    return Stream.of(methods).filter(method -> method.getName().equals(methodName)).collect(Collectors.toList());
  }

  public static Method getMethodByName(String className, String methodName) {
    var methods = getMethodsByName(className, methodName);
    if (methods.size() == 0) {
      throw new RuntimeException(String.format("Method of name %s not found", methodName));
    } else {
      var method = methods.get(0);
      method.setAccessible(true);
      return method;
    }
  }

  public static Method getMethodByNameAndType(String className, String methodName, Class<?> expectedReturnType, Class<?>... expectedArgTypes) {
    var methods = getMethodsByName(className, methodName);
    var method = methods.stream()
        .filter(m -> expectedReturnType.isAssignableFrom(m.getReturnType()))
        .filter(m -> areSubtypes(expectedArgTypes, m.getParameterTypes()))
        .findFirst().orElse(null);
    if (method == null) {
      throw new RuntimeException(String.format("Method of name %s with requested type signature not found", methodName));
    }
    method.setAccessible(true);
    return method;
  }

  private static boolean areSubtypes(Class<?>[] subtypes, Class<?>[] supertypes) {
    if (subtypes.length != supertypes.length) {
      return false;
    }
    for (int i = 0; i < subtypes.length; i++) {
      if (!supertypes[i].isAssignableFrom(subtypes[i])) {
        return false;
      }
    }
    return true;
  }

  public static Class<?> primitiveToWrapper(final Class<?> cls) {
    return primitiveWrapperMap.get(cls);
  }

  public static Constructor<?> getDefaultConstructor(String className) {
    return getConstructor(className);
  }

  public static Object getInstance(String className, Object... values) {
    var argTypes = Arrays.stream(values).map(Object::getClass).toArray(Class[]::new);
    return getInstanceWithArgType(className, argTypes, values);
  }

  /**
   * {@link #getInstance(String, Object...)} is not enough to handle constructors with primitive arguments. That's when
   * this method is preferred.
   */
  public static Object getInstanceWithArgType(String className, Class<?>[] argTypes, Object[] values) {
    Constructor<?> ctor = getConstructor(className, argTypes);
    try {
      return ctor.newInstance(values);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object getInstanceWithArgType(Class<?> clazz, Class<?>[] argTypes, Object[] values) {
    Constructor<?> ctor = getConstructor(clazz, argTypes);
    try {
      return ctor.newInstance(values);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static Constructor<?> getConstructor(String className, Class<?>... expectedArgTypes) {
    try {
      var clazz = Class.forName(className);
      var ctor = clazz.getDeclaredConstructor(expectedArgTypes);
      ctor.setAccessible(true);
      return ctor;
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... expectedArgTypes) {
    try {
      var ctor = clazz.getDeclaredConstructor(expectedArgTypes);
      ctor.setAccessible(true);
      return ctor;
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object getFieldValueByNameFromObject(Object object, String fieldName) {
    var clazz = object.getClass();
    Field field = getFieldByName(clazz.getName(), fieldName);
    try {
      return field.get(object);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object invokeMethodByNameOnObject(Object object, String methodName, Object... args) {
    var clazz = object.getClass();
    Method method = getMethodByName(clazz.getName(), methodName);
    try {
      return method.invoke(object, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
