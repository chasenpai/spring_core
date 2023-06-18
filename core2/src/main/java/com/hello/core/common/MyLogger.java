package com.hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    /**
     * request scope
     * - 해당 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다
     * - 스프링 애플리케이션을 실행 시키면 오류가 발생하는데, 싱클톤 빈은 실행 시점에 주입이 되지만
     * - request 스코프 빈은 실제 클라이언트의 요청이 와야 생성되기 때문이다
     * - 해결 방법은 Provider 를 사용하는 것이다
     *
     * proxyMode 사용
     * - 가짜 프록시 클래스를 만들어 HTTP 요청과 상관 없이 가짜 프록시 클래스를 다른 빈이 미리 주입해 둘 수 있다
     * - 가짜 프록시 객체는 내부에 진짜 myLogger 를 찾는 방법을 알고 있다
     * - 클라이언트가 myLogger.logic() 을 호출하면 사실은 가짜 프록시 객체의 메서드를 호출한 것이다
     * - 가짜 프록시 객체는 진짜 request 스코프의 진짜 myLogger.logic() 을 호출한다
     * - 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용한느 클라이언트는 원본인지 모르고 동일하게 사용 가능(다형성)
     *
     * 프록시 동작 정리
     * - CGLIB 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어 주입한다
     * - 이 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있다
     * - 가짜 프록시 객체는 실제 request 스코프와 관계가 없다. 그냥 가짜이고 내부에 단순 위임 로직만 있고 싱글톤 처럼 동작한다
     *
     * 프록시 특징
     * - provider 와 프록시 둘 중 어떤 것을 사용하든 핵심은 진짜 객체 조회를 꼭 필요한 시점까지 지연시킨다는 점이다
     * - 간단하게 어노테이션 설정만으로 원본 객체를 프록시 객체로 대체할 수 있다. 이는 다형성과 DI 컨테이너가 가진 큰 강점
     * - 꼭 웹 스코프가 아니여도 프록시는 사용할 수 있다
     * - 마치 싱글톤 처럼 동작하지만 결국 다르게 동작하기 떄문에 주의해야 함
     * - 이런 특별한 스코프는 꼭 필요한 곳에만 사용해야 한다
     */

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String msg){
        System.out.println("[" + this.uuid + "]" + "[" + this.requestURL + "] " + msg);
    }

    @PostConstruct
    public void init(){
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + this.uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("[" + this.uuid + "] request scope bean close: " + this);
    }

}
