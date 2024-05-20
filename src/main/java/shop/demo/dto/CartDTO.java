package shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartDTO {
    private Long id;
    private Long memberId;
    private CartItemDTO cartItemDTO;
    private Long quantity;

}

