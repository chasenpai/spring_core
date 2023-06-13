package com.hello.core.order;

import com.hello.core.discount.FixDiscountPolicy;
import com.hello.core.member.Grade;
import com.hello.core.member.Member;
import com.hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder(){

        /**
         * 순수한 자바 코드 테스트 시 수정자 주입을 사용하면 NullPointer 발생
         *
         * 생성자 주입을 권장하는 이유
         * - 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다
         * - 오히려 대부분의 의존관계는 종료 전까지 변하면 안된다
         * - 수정자 주입을 사용하면 setter 메소드를 public 으로 열어두어야 한다
         * - 변경하면 안되는 메소드를 열어두는 것은 좋지 않다. 실수를 방지하자
         * - 생성자 주입은 객체를 생성할 때 딱 1번 호출되므로 불변을 보장한다
         * - final 키워드를 사용할 수 있기 때문에 혹시라도 생성자에서 값이 설정되지 않는 오류를 컴파일 시점에 잡아준다
         * - 오직 생성자 주입 방식만 final 키워드 사용 가능
         * - 프레임워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살리는 법이기도 하다
         * - 필수 값이 아닐 경우 수정자 주입 방식을 추가로 부여하면 된다. 생성자와 수정자 동시 사용 가능
         */
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }

}