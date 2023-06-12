package com.hello.core.order;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    /**
     * OrderService 는 할인 정책에 대해서는 모른다
     * DiscountPolicy 가 알아서 하고 결과만 리턴
     * - 단일 책임 원칙(SRP) 준수
     *
     * 다형성 활용 및 인터페이스와 구현 객체를 분리
     * - 개방, 폐쇄 원칙(OCP) & 의존관계 역전 원칙(DIP) 준수..?
     *
     * 그렇지 않다. 추상(인터페이스) 뿐 아니라 구체(구현) 클래스에도 의존하고 있다
     * 할인 정책을 RateDiscountPolicy 로 변경 시 OrderServiceImpl 의 코드를 변경해야 한다
     * - DIP 위반
     *
     * DIP 를 위반하지 않도록 인터페이스에만 의존하게 의존관계를 변경하면 된다
     * - 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스가 필요
     */
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private  final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 프로그램 제어 흐름에 대한 권한은 모두 AppConfig 가 가지고 있다
     * OrderServiceImpl 은 생성자를 통해 어떤 구현 객체가 들어올지 알 수 없고
     * 그저 자신의 로직을 실행할 뿐이다
     * - 제어의 역전(IoC)
     */
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    /**
     * 생성자 주입
     * - 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다
     * - 불변, 필수 의존관계에 사용
     * - 생성자가 딱 한 개만 있다면  @Autowired 를 생략해도 된다(빈으로 등록된 경우)
     * - 빈을 등록하면서 의존관계 주입도 같이 일어난다
     */
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * 필드 주입
     * - 필드에 바로 주입하는 방법
     * - 코드가 간결해지지만 외부에서 변경이 불가능해서 테스트가 어렵다
     * - DI 프레임워크가 없으면 아무것도 할 수 없다
     * - 가급적 사용하지 말자
     * - 애플리케이션의 실제 코드와 관계 없는 테스트 코드, 설정 목적으로 하는 @Configuration 같은 특별한 용도로만 사용
     */
//    @Autowired
//    private final MemberRepository memberRepository;

//    @Autowired
//    private final DiscountPolicy discountPolicy;

    /**
     * 일반 메소드 주입
     * - 한번에 여러 필드를 주입 받을 수 있다
     * - 일반적으로 잘 사용하지 않는다
     */
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
