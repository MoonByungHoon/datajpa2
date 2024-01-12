package jpabasic.data_jpa_2.service;

import jpabasic.data_jpa_2.domain.Member;
import jpabasic.data_jpa_2.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  public void 회원가입() throws Exception {
    //given
    Member member = new Member("Moon");

    //when
    Long saveMemberId = memberService.join(member);

    //then
    assertEquals(member, memberRepository.findOne(saveMemberId));
  }

  @Test
  public void 중복_회원_예외() throws Exception {
    //given
    Member member1 = new Member("Kim");
    Member member2 = new Member("Kim");

    //when
    memberService.join(member1);
    assertThrows(IllegalStateException.class, () -> {
      memberService.join(member2);
    });

    //then
//    예외가 발생하지 않으면 아래 fail이 동작하고
//    assert에서 제공하는 fail에 의해서 강제로 에러가 발생한다.
      fail("예외가 발생해야 한다.");
  }
}