package shop.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime date;
    private List<Long> orders;
    private List<Long> carts;
}
