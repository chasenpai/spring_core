package com.hello.core.singleton;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){

        AppConfig appConfig = new AppConfig();

        //1. 조회 - 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회 - 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인
        assertThat(memberService1).isNotSameAs(memberService2);

        /**
         * 순수한 DI 컨테이너인 AppConfig 는 요청을 할 때 마다 객체를 새로 생성한다
         * - 메모리의 낭비
         *
         * 객체가 딱 1개만 생성되고 공유하도록 설계해야 한다
         * - 싱글톤 패턴
         * - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
         * - 이미 만들어진 객체를 공유해서 효율적으로 사용 한다
         *
         * 싱글톤 패턴의 문제점
         * - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다
         * - 의존관계상 클라이언트가 구체 클래스에 의존 > DIP 위반
         * - 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 수 있다
         * - 테스트 하기 어렵고 내부 속성을 변경하거나 초기화하기 어려움
         * - private 생성자로 자식 클래스를 만들기 어렵다
         * - 결론적으로 유연성이 떨어지며 안티패턴으로 불리기도 한다다
         */

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){

        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isEqualTo(singletonService2);

    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //조회 - 호출할 때 마다 항상 같은 객체를 반환
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 같은 것을 확인
        assertThat(memberService1).isEqualTo(memberService2);

        /**
         * 스프링 컨테이너는 기본적으로 싱글톤으로 객체들을 관리한다
         * 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라 부른다
         * 스프링 컨테이너 덕분에 싱글톤 패턴의 단점들을 모두 해결할 수 있다
         */

    }

}
