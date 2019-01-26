package org.litespring.beans;

import java.util.List;

public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();
    boolean isPrototype();
    String getScope();
    void setScope(String scope);

    String getBeanClassName();

    // setter 注入
    List<PropertyValue> getPropertyValues();

    // constructor 注入
    ConstructorArgument getConstructorArgument();

    String getID();

    boolean hasConstructorArgumentValues();

    // 注解扫描
    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;
    Class<?> getBeanClass() throws IllegalStateException ;
    boolean hasBeanClass();

    boolean isSynthetic();
}
