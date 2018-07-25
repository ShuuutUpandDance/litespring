package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry{

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public DefaultBeanFactory() {
    }

    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }

    public Object getBean(String beanID) {
        // 根据 id 创建对应的 bean 对象
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if(bd == null){
            throw new BeanCreationException("Bean Definition does not exists");
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed");
        }
    }
}
