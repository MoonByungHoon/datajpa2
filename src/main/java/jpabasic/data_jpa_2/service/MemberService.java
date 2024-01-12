package jpabasic.data_jpa_2.service;

import jpabasic.data_jpa_2.domain.Member;
import jpabasic.data_jpa_2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//현재 비지니스 로직상 기능들이 조회 기능이 많기 때문에
//jpa가 직접 성능 최적화를 진행해줄 수 있게 읽기 전용으로 트랜잭션 설정을 해준 것이다.
@RequiredArgsConstructor
public class MemberService {

//  repository를 직접 Autowired를 걸게 되면 테스트 코드를 작성할때에 불편한 경우가 생긴다.
//  테스트 repository에 대한 테스트가 힘들기 때문에 생성자로 걸어주는 것이 좋다.
//  @Autowired
  private final MemberRepository memberRepository;

  //회원가입
//  트랜젝션 어노테이션의 경우 기본 값이 readOnly = false 이기 때문에
//  단순히 어노테이션만 적용해도 수정 가능한 상태로 부여해준다.
  @Transactional
  public Long join(Member member) {
    validateDuplicdateMember(member); //중복 회원 검증
    memberRepository.save(member);

    return member.getId();
  }

  private void validateDuplicdateMember(Member member) {
    //중복 이름에 대한 익셉션 처리
    List<Member> findMembers = memberRepository.findByName(member.getName());

    if(!findMembers.isEmpty()){
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  //회원 전체 조회
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }
}
