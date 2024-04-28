package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Order;
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

    @GetMapping("/orders/member")
    public ResponseEntity<List<Order>> getOrdersByMemberId(Long memberId) {
        List<Order> orders = orderService.viewOrdersByMemberId(memberId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addOrderFromCart(Long memberId,
                                                 @RequestParam Long zipcode,
                                                 @RequestParam String detail,
                                                 @RequestParam Long cardnum) {
        Long orderId = orderService.addorderfromcart(memberId, zipcode, detail, cardnum);
        return ResponseEntity.ok(orderId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeOrder(Long memberId) {
        orderService.removeOrder(memberId);
        return ResponseEntity.noContent().build();
    }
}