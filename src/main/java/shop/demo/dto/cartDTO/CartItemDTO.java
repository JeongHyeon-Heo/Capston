package shop.demo.dto.cartDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.demo.domain.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long itemId;
    private String name;
    private int price;
    private Category category;
    private String imageUrl;

}
