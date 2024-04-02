package com.example.itemShopApi.service;

import com.example.itemShopApi.domain.Member;
import com.example.itemShopApi.domain.Role;
import com.example.itemShopApi.repository.MemberRepository;
import com.example.itemShopApi.repository.RoleRepository;
import com.fasterxml.classmate.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true) //조회 전용 , 다른 업무는 트랜잭션이 못함
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        //orElseThrow 앞 메서드의 결과값이 없으면
        //이 부분은 DB에 값이 없는 경우를 예외처리한것으로 꼭 알아두어야 하는 방법이다.
    }

    @Transactional
    public Member addMember(Member member){
        //권한들중 ROLE_USER를 가져와서 member에 넣어준다.
        //이후 권한값을 추가한 member를 save한다.
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");

        /**
         * Member_role에도 저장하는법을 살펴보자
         * ROLE USER 정보를 ROLE 에서 가져온다.
         * 이후 Member엔티티에 Role값을 메서드를 통해 넣어준다
         * Member는 Role 엔티티를 가질수 있는 Set이 있다.
         * 그래서 받아온 Role 엔티티를 member안에 roles 에 추가해준다
         * 이 상태에서 role 은 DB에 저장되 되있고 member는 아직 저장이 안됫다
         * 그래서 role을 가진 member 를 저장하는데
         * member 와 role을 매핑시키기 위해서 member_role 이 필요하다
         */
        
        member.addRole(userRole.get());

        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    @Transactional
    public Member addRoleMember(Member member){
        Optional<Role> userRole = roleRepository.findByName("ROLE_ADMIN");

        member.addRole(userRole.get());

        Member saveMember = memberRepository.save(member);
        return saveMember;
    }



    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId){
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMember(String email){
        return memberRepository.findByEmail(email);
    }



}
