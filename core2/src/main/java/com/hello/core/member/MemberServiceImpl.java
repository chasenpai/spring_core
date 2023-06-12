package com.hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * setter 주입
     * - 선택, 변경 가능성이 있는 의존관계에 사용
     * - 자바빈 프로퍼티 규약의 수정자 메소드 방식을 사용하는 방법
     * - @Autowired 의 기본 동작은 주입할 대상이 없으면 오류가 발생한다
     * - 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)
     */
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
