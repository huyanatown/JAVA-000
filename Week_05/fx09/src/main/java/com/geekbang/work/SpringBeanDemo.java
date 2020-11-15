package com.geekbang.work;

import com.geekbang.work.entity.Student;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.UnsupportedEncodingException;

@PropertySource("META-INF/student-annotation.properties")
public class SpringBeanDemo {
    @Bean
    public Student getStudent(@Value("${student.id}")int id, @Value("${student.name}")String name){
        Student student = new Student();
        student.setId(id);
        try {
            name = new String(name.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        student.setName(name);
        return student;
    }

    public static void main(String[] args) {
        loadBeanFromAnnotation();

    }



    public static void loadBeanFromAnnotation(){
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringBeanDemo.class);
        applicationContext.refresh();
        final Student student = applicationContext.getBean("getStudent", Student.class);
        System.out.println(student);
        applicationContext.close();
    }
    public static void loadBeanFromBeanDefinition(){
        final BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Student.class);
        final AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.addPropertyValue("id", "3")
                .addPropertyValue("name", "汤辉")
                .getBeanDefinition();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("student", beanDefinition);
        final Student student = beanFactory.getBean("student", Student.class);
        System.out.println(student);
    }

    public static void loadBeanFromProperties(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("META-INF/Student.properties");
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        reader.loadBeanDefinitions(encodedResource);
        final Student student = beanFactory.getBean("student", Student.class);
        System.out.println(student);
    }

    public static void loadBeanFromXml(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/spring-context.xml");
        final Student student = beanFactory.getBean("student", Student.class);
        System.out.println(student);
    }
}
