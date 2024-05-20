package shop.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* 회원 주소 수정 5.19 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDTO {
    private String newAddress;
}
