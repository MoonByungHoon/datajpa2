package jpabasic.data_jpa_2.api;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jpabasic.data_jpa_2.controller.AddressDTO;
import jpabasic.data_jpa_2.controller.MemberForm;
import jpabasic.data_jpa_2.domain.Address;
import jpabasic.data_jpa_2.domain.Member;
import jpabasic.data_jpa_2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

//@ResponseBody @Controller 2개를 합친 것이 @Restcontroller이다.
@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;
  private final EntityManager em;

  @GetMapping("/api/v1/members")
  public List<Member> membersV1() {
    return memberService.findMembers();
  }
  @GetMapping("/api/v2/members")
  public Result membersV2() {
    List<MemberDTO> collect = memberService.findMembers().stream()
            .map(m -> new MemberDTO(m.getName()))
            .collect(Collectors.toList());

    return new Result(collect.size(), collect);
  }

  @Data
  @AllArgsConstructor
  public class Result<T> {
    private int count;
    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDTO {
    private String name;
  }

  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

    return new CreateMemberResponse(memberService.join(member));
  }

  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

    Member member = new Member();
    member.setName(request.getName());

    return new CreateMemberResponse(memberService.join(member));
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request) {

    memberService.update(id, request.getName());

    Member findMember = memberService.findOne(id);

    return new UpdateMemberResponse(findMember.getId(), findMember.getName());
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  static class CreateMemberRequest {
    private String name;
  }
  @Data
  static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }
}
