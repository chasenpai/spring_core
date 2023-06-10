package com.hello.core.beandefinition;

import com.hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){

        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(name);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + name + " beanDefinition = " + beanDefinition);
            }
        }
        /**
         * BeanDefinition - 스프링이 다양한 형태의 설정 정보를 BeanDefinition 으로 추상화 해서 사용한다
         *
         * BeanClassName - 생성할 빈의 클래스명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
         * factoryBeanName - 팩토리 역할의 빈을 사용할 경우 이름
         * factoryMethodName - 빈을 생성할 팩토리 메소드 지정
         * Scope : 싱클톤(기본값)
         * lazyInit - 스프링 컨테이너를 생성할 때 빈을 생성하는게 아닌 실제 빈을 사용할 때 까지 지연처리 여부
         * InitMethodName - 빈을 생성하고 의존관계를 적용한 뒤 호출되는 메소드명
         * DestroyMethodName - 빈의 생명주기가 끝나서 제거하기 직전 호출되는 메소드명
         * Constructor arguments, Properties - 의존관계 주입에서 사용(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
         *
         * BeanDefinition 을 직접 정의해서 스프링 컨테이너에 등록할 수 있다(그럴 일은 거의 없다)
         */

    }

}



