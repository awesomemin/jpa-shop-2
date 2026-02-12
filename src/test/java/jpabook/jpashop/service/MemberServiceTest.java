package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("member1");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복회원예외() throws Exception {
        // given
        String duplicatedUsername = "duplicated_username";

        Member originalMember = new Member();
        originalMember.setName(duplicatedUsername);
        memberService.join(originalMember);

        // when
        Member newMember = new Member();
        newMember.setName(duplicatedUsername);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(newMember));
    }

}