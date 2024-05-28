package shop.demo.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String name;
    private String email;
    private String password;
    private String address;
    private LocalDateTime date;

}