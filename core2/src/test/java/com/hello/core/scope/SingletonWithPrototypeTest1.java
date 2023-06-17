package com.hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        //assertThat(count2).isEqualTo(2);
        assertThat(count2).isEqualTo(1);


    }

    @Scope("singleton")
    static class ClientBean{

        /**
         * 싱글톤 빈이 프로토타입 빈을 사용하게 되기 때문에 프로토타입 빈이 새로 생성되기는 하지만
         * 싱클톤 빈과 함께 유지되는 것이 문제다.
         *
         * 스프링 컨테이너에 요청
         * - 가장 간단한 방법으로 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에서 새로 요청하는 것이다
         *
         * ObjectFactory, ObjectProvider 사용
         * - 지정한 빈을 컨테이너에서 대신 찾아주는 DL(Dependency Lookup) 서비스를 제공한다
         * - ObjectFactory : 기능이 단순하고 별도의 라이브러리 필요 없음. 스프링에 의존
         * - ObjectProvider : ObjectFactory 를 상속하고, 옵션, 스트림 처리등 편의 기능이 많다
         *
         * JSR-330 Provider
         * - 자바 표준을 사용하는 방법
         * - 간단하지만 별도의 라이브러리가 필요하다
         *
         * 프로토타입 빈을 언제 사용할까?
         * - 매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요하면 사용하면 된다
         * - 하지만 실무에서 웹 애플리케이션 개발 시 사용할 일은 드물다
         * - ObjectProvider, JSR-330 Provider 은 프로토타입이 아니더라도 DL 이 필요할 때 사용할 수 있다
         */
        //private final PrototypeBean prototypeBean;

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        private Provider<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean){
//            this.prototypeBean = prototypeBean;
//        }

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount(){
            this.count++;
        }

        public int getCount(){
            return this.count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }
        
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }

    }

}
