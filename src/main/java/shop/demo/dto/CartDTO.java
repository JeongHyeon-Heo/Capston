package shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDTO {
    private Long id;
    private Long memberId;
    private Long itemId;
    private Long quantity;

}

