package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try{
            is = resource.getInputStream();

            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement(); //<beans>
            @SuppressWarnings("unchecked")
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                this.beanDefinitionRegistry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document", e);
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
