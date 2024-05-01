package shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long memberId;
    private Long addressId;
    private Long paymentId;
    private List<Long> orderItemIds;
    private LocalDateTime date;

}