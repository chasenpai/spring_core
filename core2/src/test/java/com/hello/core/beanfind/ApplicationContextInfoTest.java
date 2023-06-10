package com.hello.core.beanfind;

import com.hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력")
    void findAllBean(){

        String[] allBeanNames = ac.getBeanDefinitionNames();
        
        for (String beanName : allBeanNames) {
            Object bean = ac.getBean(beanName);
            System.out.println("beanName = " + beanName + " object = " + bean);
        }

    }

    @Test
    @DisplayName("애플리케이션 빈 출력")
    void findApplicationBean(){

        String[] allBeanNames = ac.getBeanDefinitionNames();

        for (String beanName : allBeanNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanName);

            /**
             * ROLE_APPLICATION - 사용자가 등록한 빈
             * ROLE_INFRASTRUCTURE - 스프링이 내부에서 사용하는 빈
             */
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanName);
                System.out.println("beanName = " + beanName + " object = " + bean);
            }
        }

    }

}
