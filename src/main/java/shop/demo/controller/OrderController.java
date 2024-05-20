package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Member;
import shop.demo.domain.Order;
import shop.demo.dto.OrderCardDTO;
import shop.demo.dto.OrderDTO;
import shop.demo.dto.OrderSelectCartDTO;
import shop.demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/member")
    public ResponseEntity<List<OrderDTO>> getOrdersByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<OrderDTO> orderDTOs = orderService.viewOrdersByMemberId(memberId);
        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addOrderFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody OrderCardDTO orderCardDTO) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long orderId = orderService.addorderfromcart(memberId, orderCardDTO.getCardnum());
        return ResponseEntity.ok(orderId);
    }
    @PostMapping("/addselectcart")
    public ResponseEntity<Long> addOrderSelectCart(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody OrderSelectCartDTO orderSelectCartDTO) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long orderId = orderService.addOrderFromSelectCart(memberId, orderSelectCartDTO.getCardnum(), orderSelectCartDTO.getCartAddDTOS());
        return ResponseEntity.ok(orderId);
    }

    @PutMapping("/cancel/{orderid}")
    public ResponseEntity<String> cancelOrder(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long orderid) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<Long> orderIds = orderService.getOrderIdsByMemberId(memberId);
        // 요청된 orderId가 현재 사용자의 오더 아이디 목록에 포함되어 있는지 확인합니다.
        if (orderIds.contains(orderid)) {
            // 현재 사용자의 오더이므로 삭제합니다.
            orderService.cancelOrder(orderid);
            return ResponseEntity.ok("Order cancelled successfully");
        } else {
            // 요청된 orderId가 현재 사용자의 것이 아니므로 삭제 권한이 없음을 알립니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/remove/{orderid}")
    public ResponseEntity<Void> removeOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long orderid) {

        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<Long> orderIds = orderService.getOrderIdsByMemberId(memberId);

        // 요청된 orderId가 현재 사용자의 오더 아이디 목록에 포함되어 있는지 확인합니다.
        if (orderIds.contains(orderid)) {
            // 현재 사용자의 오더이므로 삭제합니다.
            orderService.removeOrder(orderid);
            return ResponseEntity.noContent().build();
        } else {
            // 요청된 orderId가 현재 사용자의 것이 아니므로 삭제 권한이 없음을 알립니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}