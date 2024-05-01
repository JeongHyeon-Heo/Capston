package shop.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Long quantity;


    public void setMember(Member member) {
        this.member = member;
        member.getCarts().add(this);
    }

    public static Cart createcart(Member member, Item item, Long quantity) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setItem(item);
        cart.setQuantity(quantity);

        return cart;
    }
}
