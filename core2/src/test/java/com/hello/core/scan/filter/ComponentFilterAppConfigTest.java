package com.hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));

    }

    @Configuration
    @ComponentScan(
            //컴포넌트 스캔 필터 지정
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
            /**
             * ANNOTATION - 기본값, 어노테이션 인식
             * ASSIGNABLE_TYPE - 지정한 타입과 자식 타입 인식
             * ASPECTJ - AspectJ 패턴 사용
             * REGEX - 정규 표현식
             * CUSTOM -  TypeFilter 인터페이스 구현해서 처리
             *
             * 스프링 부트는 컴포넌트 스캔을 기본으로 제공하기 때문에 옵션을 변경하기 보단 스프링 기본 설정에
             * 최대한 맞추어 사용하는 것을 권장장
             */
    )
    static class ComponentFilterAppConfig {

    }

}
