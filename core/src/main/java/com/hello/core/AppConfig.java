package com.hello.core;

import com.hello.core.discount.FixDiscountPolicy;
import com.hello.core.discount.RateDiscountPolicy;
import com.hello.core.member.MemberService;
import com.hello.core.member.MemberServiceImpl;
import com.hello.core.member.MemoryMemberRepository;
import com.hello.core.order.OrderService;
import com.hello.core.order.OrderServiceImpl;

public class AppConfig {

    /**
     * AppConfig 는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다
     * 생성한 객체 인스턴스의 참조를 생성자를 통해서 연결 해준다
     */
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    /**
     * OrderServiceImpl 입장에서 봤을 때, 의존관계를 마치 외부에서 주입해주는 것 같다
     * - 의존성 주입(DI)
     */
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
