package com.hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작 시 호출출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String message){
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    /**
     * 그냥 어노테이션 쓰세요
     * - 최신 스프링에서 권장하는 방식
     * - 스프링 종속 기술이 아니라 자바 표준이기 때문에 스프링이 아닌 다른 컨테이너에서도 동작한다
     * - 컴포넌트 스캔과 잘어울린다
     * - 유일한 단점은 라이브러리에 적용할 수 없는 점
     * - 외부라이브러리를 초기화, 종료 해야 하면 @Bean 의 설정 기능을 사용하자
     */
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

    /**
     * 초기화, 소멸 인터페이스의 단점
     * - 스프링 전용 인터페이스이기 때문에 해당 코드가 스프링 전용 인터페이스에 의존한다
     * - 초기화, 소멸 메소드의 이름을 변경할 수 없음
     * - 내가 코드를 고칠 수 없는 외부 라이브러리에 적용 불가
     * - 요즘은 거의 사용 안함
     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

}
