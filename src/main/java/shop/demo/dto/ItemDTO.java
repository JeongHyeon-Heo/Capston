package shop.demo.dto;

import lombok.Getter;
import lombok.Setter;
import shop.demo.domain.Category;

@Getter @Setter
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Category category;
}