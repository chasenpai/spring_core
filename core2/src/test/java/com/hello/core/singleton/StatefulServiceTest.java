package com.hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //Thread A - 사용자 A 10000원 주문
        statefulService1.order("userA", 10000);
        //Thread B - 사용자 B 20000원 주문
        statefulService2.order("userB", 20000);

        //Thread A - 사용자 A 주문 금액 조회 > 10000 원이 아닌 20000 원 출력 (문제 발생!)
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        /**
         * 싱글톤 방식의 주의점
         * - 싱글톤 객체는 상태를 유지하지 않는 무상태로 설계해야 한다
         * - 특정 클라이언트에 의존적인 필드가 있으면 안됨
         * - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안됨
         * - 가급적 일긱만 가능해야 한다
         * - 필드 대신 지역변수, 파라미터, 쓰레드 로컬등을 사용해야 한다
         * - 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다
         */

    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }

}