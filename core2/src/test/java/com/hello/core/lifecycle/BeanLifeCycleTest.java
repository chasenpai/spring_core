package com.hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){

        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        /**
         * 스프링 빈이 가지는 생명주기
         * - 객체 생성 > 의존관계 주입
         * - 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메소드를 통해 초기화 시점을 알려주는 다양한 기능 제공
         * - 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다
         * - 따라서 안전하게 종료 작업 진행 가능
         *
         * 스프링 빈의 이벤트 생명주기
         * - 스프링 컨테이너 생성 > 스프링 빈 생성 > 의존관계 주입 > 초기화 콜백 > 사용 > 소멸전 콜백 > 스프링 종료
         *
         * 객체의 생성과 초기화를 분리하자
         * - 생성자 내에서 무거운 초기화 작업을 함께 하는 것을 지양하자
         */
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig{

        /**
         * 설정 정보를 사용하는 초기화, 소멸 콜백
         * - 메소드 이름을 자유롭게 줄 수 있다
         * - 스프링 빈이 스프링 코드에 의존하지 않는다
         * - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메소드 적용 가능
         *
         * 종료 메소드 추론
         * - 라이브러리는 대부분 close, shutdown 이라는 이름의 종료 메소드를 사용
         * - @Bean 의 destroyMethod 기본값이 (inferred) 추론으로 등록되어 있다
         * - 추론 기능이 close, shutdown 이라는 이름의 메소드를 자동으로 호출해준다
         * - 직접 스프링 빈으로 등록하면 종료 메소드는 따로 적어주지 않아도 동작한다
         * - 추론 기능을 사용하지 않으려면 destroyMethod = "" 공백으로 값을 지정하면 된다
         */
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient(){

            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-srping.dev");

            return networkClient;
        }

    }

}
