package jpabasic.data_jpa_2.controller;

import jpabasic.data_jpa_2.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressDTO {

  private String city;
  private String street;
  private String zipcode;

  public AddressDTO(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }

  public Address changeToAddress(AddressDTO addressDTO) {
    return new Address(addressDTO.getCity(), addressDTO.getStreet(), addressDTO.zipcode);
  }
}
