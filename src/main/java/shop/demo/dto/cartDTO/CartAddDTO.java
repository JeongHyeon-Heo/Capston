package shop.demo.dto.cartDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartAddDTO {
    private Long itemId;
    private Long quantity;
}
