package shop.demo.dto.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.demo.dto.cartDTO.CartAddDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSelectCartDTO {
    private Long cardnum;
    private String address;
    private List<CartAddDTO> cartAddDTOS;
}
