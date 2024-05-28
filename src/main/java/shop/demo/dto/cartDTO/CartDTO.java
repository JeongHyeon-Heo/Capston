package shop.demo.dto.cartDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDTO {
    private Long id;
    private Long memberId;
    private CartItemDTO cartItemDTO;
    private Long quantity;

}

