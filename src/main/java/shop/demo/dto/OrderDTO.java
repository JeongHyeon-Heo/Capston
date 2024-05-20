package shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.demo.domain.OrderState;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long memberId;
    private String address;
    private Long card;
    private Long amountpay;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime date;
    private OrderState orderState;

}