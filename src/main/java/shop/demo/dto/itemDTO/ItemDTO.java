package shop.demo.dto.itemDTO;

import lombok.Getter;
import lombok.Setter;
import shop.demo.domain.Category;

//ItemController와 ItemService 간의 데이터 교환을 중개하는 역할
@Getter @Setter
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Category category;
    private String imagePath;
}