package shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.demo.domain.OrderState;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long memberId;
    private String address;
    private Long paymentId;
    private List<Long> orderItemIds;
    private LocalDateTime date;
    private OrderState orderState;

}