package jpabasic.data_jpa_2.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

  @NotEmpty(message = "회원 이름은 필수입니다.")
  private String name;
  private AddressDTO addressDTO;

  public AddressDTO changeToAddressDTO(MemberForm form) {
    return new AddressDTO(form.addressDTO.getCity(), form.addressDTO.getStreet(), form.addressDTO.getZipcode());
  }
}
