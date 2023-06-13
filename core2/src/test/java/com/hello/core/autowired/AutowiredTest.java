package com.hello.core.autowired;

import com.hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption(){

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{

        /**
         * 옵션 처리
         * - 주입할 스프링 빈이 없어도 동작해야 할 때가 있다
         * - @Autowired 의 required 의 기본값이 true 로 되어 있어서 자동 주입 대상이 없으면 오류가 발생
         * - @Nullable : 자동 주입할 대상이 없으면 null 이 입력 된다
         * - Optional : 자동 주입할 대상이 없으면 Optional.empty 가 입력 된다
         */
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){ //Member 는 스프링 빈이 아니다. required = false 이므로 호출 자체가 안됌
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }

    }

}
