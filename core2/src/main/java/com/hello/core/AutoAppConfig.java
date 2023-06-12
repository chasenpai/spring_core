package com.hello.core;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemoryMemberRepository;
import com.hello.core.order.OrderService;
import com.hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( //@Component 어노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다
        basePackages = "com.hello.core", //스캔을 시작할 패키지 지정
        basePackageClasses = AutoAppConfig.class, //지정한 클래스의 패키지를 스캔 시작 위치로 지정
        /**
         * 스캔 시작 위치 미지정 시 @ComponentScan 이 붙은 클래스가 시작 위치가 됌
         * 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것을 권장
         * 스프링 부트의 경우 시작인 @SpringBootApplication 안에 @ComponentScan 이 들어있다
         *
         * 컴포넌트 스캔 기본 대상
         * - 해당 어노테이션이 있으면 스프링이 부가 기능 수행
         * @Component - 컴포넌트 스캔에서 사용
         * @Controller - 스프링 MVC 컨트롤러에서 사용, 스프링 MVC 컨트롤러로 인식
         * @Service - 스프링 비즈니스 로직에서 사용, 특별한 처리를 하진 않지만 핵심 비즈니스 로직이 여기 있겠구나 인식
         * @Repository - 스프링 데이터 접근 계층에서 사용, 데이터 계층의 예외를 스프링 예외로 변환
         * @Configuration - 스프링 설정 정보에서 사용, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 함
        */
        //기존 AppConfig 예제와 충돌 방지를 위해 선언(@Configuration 도 @Component 가 붙어있다)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

        /**
         * 수동 빈 등록 vs 자동 빈 등록
         * - Overriding bean definition for benn 'memoryMemberRepository' ...
         * - 수동 빈이 자동 빈을 오버라이딩 해버린다
         * - 실제로 개발자가 의도적으로 설정해서 이런 결과가 나오기 보단 여러 설정들이 꼬여서 생기는 일이 대부분
         *
         * 스프링 부트는 기본값으로 빈 오버라이딩이 false 로 되어 있다
         */
        @Bean(name = "memoryMemberRepository")
        MemberRepository memberRepository(){
            return new MemoryMemberRepository();
        }

        @Autowired
        MemberRepository memberRepository;
        @Autowired
        DiscountPolicy discountPolicy;

        @Bean
        OrderService orderService(){
            return new OrderServiceImpl(memberRepository, discountPolicy);
        }

}
