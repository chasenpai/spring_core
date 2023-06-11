package com.hello.core.singleton;

public class SingletonService {

    //static 영억에 객체 인스턴스를 미리 하나 생성해서 올린다
    private static final SingletonService instance = new SingletonService();

    //이 객체의 인스턴스가 필요하면 오직 getInstance 메소드를 통해서만 조회할 수 있다(항상 같은 인스턴스 반환)
    public static SingletonService getInstance(){
        return instance;
    }

    //생성자를 private 으로 선언하여 외부에서 객체 인스턴스가 생성되는 것을 막는다
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }

}
