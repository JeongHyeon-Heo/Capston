package shop.demo.dto.orderDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.demo.domain.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long itemid;
    private String itemname;
    private int itemprice;
    private Category itemcategory;
    private int quantity;
    private String imageUrl;
}
