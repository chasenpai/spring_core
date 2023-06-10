package com.hello.core;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.discount.RateDiscountPolicy;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemberService;
import com.hello.core.member.MemberServiceImpl;
import com.hello.core.member.MemoryMemberRepository;
import com.hello.core.order.OrderService;
import com.hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * 스프링 컨테이너는 @Configuration 이 붙은 구성 정보를 사용하여 @Bean 이라 적힌
     * 메소드를 모두 호출해서 반한된 객체를 스프링 컨테이너에 등록한다
     * 스프링 컨테이너에 등록된 객체를 스프링 빈이라 부른다
     */

    @Bean
    public MemberService memberService() { //기본적으로 메소드 이름으로 빈이 등록 된다(빈 이름은 중복되면 안됌)
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
