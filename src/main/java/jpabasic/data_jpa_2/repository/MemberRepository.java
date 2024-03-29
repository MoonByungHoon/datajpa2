package jpabasic.data_jpa_2.repository;

import jpabasic.data_jpa_2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
  List<Member> findByName(String name);
}
