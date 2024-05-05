package shop.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id; //아이템 고유번호

    private String name; //상품 이름

    private int price; //상품 가격

    private int stockQuantity; //재고량

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; //상품 품절 여부

    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category; //카테고리


    //주문된 상품들
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //장바구니에 담긴 상품들
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();


    //재고 추가 => 품절관리 코드 추가시 크게 사용할 필요가 없어서 일단 주석처리.
    /*public void addStock(int quantity){
        this.stockQuantity += quantity;
    }*/

    //재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        this.stockQuantity = restStock;
    }
}
