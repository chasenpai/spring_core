package com.hello.core.singleton;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemberServiceImpl;
import com.hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        //세개가 모두 같다
        System.out.println("memberService --> memberRepository = " + memberRepository1);
        System.out.println("orderService --> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    @Test
    void configurationDeep(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean.getClass()); //class com.hello.core.AppConfig$$SpringCGLIB$$0

        /**
         * @Configuration 어노테이션을 사용하면
         * 스프링이 CGLIB 라는 바이트코드 조작 라이브러리를 사용하여 AppConfig 클래스를
         * 상속받은 임의의 클래스를 만들고 그 클래스를 스프링 빈으로 등록한다
         * - 그 임의의 클래스가 싱글톤이 보장되도록 해준다
         * - @Bean 붙은 메소드마다 이미 스플이 빈이 존재하면 존재하는 빈을 반환하고 없으면 생성해서
         * 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다
         */

    }

}
