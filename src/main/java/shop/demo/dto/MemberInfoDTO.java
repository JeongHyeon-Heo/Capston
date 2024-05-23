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
    private String address;
    private LocalDateTime date;
    /* 수정 5.19 */
    private List<OrderDTO> orderDTOS;
    /* 수정 5.19 */
}
