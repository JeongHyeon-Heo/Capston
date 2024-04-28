package shop.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id; // 아이템 고유번호

    private String name; // 상품이름

    private int price; // 가격

    private int stockQuantity; //재고량

    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category; //카테고리


    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        this.stockQuantity = restStock;
    }
}
