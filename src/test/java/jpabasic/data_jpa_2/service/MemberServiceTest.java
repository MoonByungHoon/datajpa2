package jpabasic.data_jpa_2.service;

import jpabasic.data_jpa_2.domain.Member;
import jpabasic.data_jpa_2.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired MemberService memberService;
  @Autowired
  MemberRepositoryOld memberRepository;

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

    //when, then
    memberService.join(member1);
    assertThrows(IllegalStateException.class, () -> {
      memberService.join(member2);
    });
  }
}