package com.hello.core.order;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
/**
 * @RequiredArgsConstructor
 * - final 키워드가 붙은 필드로 자동으로 생성자를 만들어줌
 * - 생성자가 딱 한개라면 @Autowired 생략 가능
 * - 생성자를 딱 한개만 두고 @Autowired 를 생략하는 방법을 주로 사용한다
 * - 코드가 깔끔해지고 의존성 주입이 편해진다
 */
//@RequiredArgsConstructor
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

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy){
//        this.discountPolicy = discountPolicy;
//    }

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
     *
     * 조회 빈이 2개 이상 문제
     * - @Autowired 는 Type 으로 조회한다
     * - FixDiscountPolicy, RateDiscountPolicy 둘다 빈 등록 시 NoUniqueBeanDefinitionException 발생
     * - @Autowired 는 타입 매칭을 시도하고 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭한다
     *
     * @Qualifier 사용
     *  - 추가 구분자를 붙여주는 방법으로, 주입 시 추가적인 방법을 제공하는 것이지 빈 이름을 변경하는 것은 아님
     *  - 만약 @Qualifier("mainDiscountPolicy") 를 못찾으면 mainDiscountPolicy 라는 이름의 빈을 추가로 찾는다
     *  - 단점으로는 주입 받을 때 모든 코드에 어노테이션을 붙여줘야 한다
     *
     * @Primary 사용
     * - 우선 순위를 지정하는 방법으로, @Primary 가 붙은 빈이 우선권을 가진다
     * - 스프링은 자동보다 수동이, 넓은 범위의 선택권 보다는 좁은 범위의 선택권이 우선 순위가 높다
     * - 따라서 @Primary 보다 @Qualifier 의 우선 순위가 높다
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
