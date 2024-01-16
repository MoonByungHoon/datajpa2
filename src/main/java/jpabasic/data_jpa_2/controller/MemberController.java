package jpabasic.data_jpa_2.controller;

import jakarta.validation.Valid;
import jpabasic.data_jpa_2.domain.Address;
import jpabasic.data_jpa_2.domain.Member;
import jpabasic.data_jpa_2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult result) {

    if (result.hasErrors()) {
      return "members/createMemberForm";
    }

    AddressDTO addressDTO = form.changeToAddressDTO(form);

    memberService.join(new Member(form.getName(), addressDTO.changeToAddress(addressDTO)));

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    model.addAttribute("members", memberService.findMembers());
    return "members/memberList";
  }
}
