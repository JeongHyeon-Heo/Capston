package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Order;
import shop.demo.dto.OrderDTO;
import shop.demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
/*
    @GetMapping("/orders/member")
    public ResponseEntity<List<Order>> getOrdersByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        List<Order> orders = orderService.viewOrdersByMemberId(memberId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addOrderFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestParam Long zipcode,
                                                 @RequestParam String detail,
                                                 @RequestParam Long cardnum) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        Long orderId = orderService.addorderfromcart(memberId, zipcode, detail, cardnum);
        return ResponseEntity.ok(orderId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        orderService.removeOrder(memberId);
        return ResponseEntity.noContent().build();
    }
*/

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByMemberId(@PathVariable Long memberId) {
        List<OrderDTO> orderDTOs = orderService.viewOrdersByMemberId(memberId);
//        List<OrderDTO> orderDTOs = orderService.viewOrdersByMemberId(memberId);
        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping("/add/{memberId}")
    public ResponseEntity<Long> addOrderFromCart(@PathVariable Long memberId,
                                                 @RequestParam Long zipcode,
                                                 @RequestParam String detail,
                                                 @RequestParam Long cardnum) {
        Long orderId = orderService.addorderfromcart(memberId, zipcode, detail, cardnum);
        return ResponseEntity.ok(orderId);
    }

    @DeleteMapping("/remove/{orderid}")
    public ResponseEntity<Void> removeOrder(@PathVariable Long orderid) {
        orderService.removeOrder(orderid);
        return ResponseEntity.noContent().build();
    }
}