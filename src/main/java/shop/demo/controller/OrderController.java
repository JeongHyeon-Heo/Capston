package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Member;
import shop.demo.dto.orderDTO.OrderCardDTO;
import shop.demo.dto.orderDTO.OrderDTO;
import shop.demo.dto.orderDTO.OrderSelectCartDTO;
import shop.demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //현재 멤버의 오더 조회
    @GetMapping("/member")
    public ResponseEntity<List<OrderDTO>> getOrdersByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<OrderDTO> orderDTOs = orderService.viewOrdersByMemberId(memberId);
        return ResponseEntity.ok(orderDTOs);
    }

    //현재 멤버의 카트에 있는 내용을 모두 오더에 추가
    @PostMapping("/add")
    public ResponseEntity<Long> addOrderFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody OrderCardDTO orderCardDTO) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long orderId = orderService.addorderfromcart(memberId, orderCardDTO.getCardnum());
        return ResponseEntity.ok(orderId);
    }

    //현재 맴버가 선택한 아이템을 오더에 추가
    @PostMapping("/addselect")
    public ResponseEntity<Long> addOrderSelect(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody OrderSelectCartDTO orderSelectCartDTO) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long orderId = orderService.addOrderFromSelectCart(
                memberId, orderSelectCartDTO.getCardnum(), orderSelectCartDTO.getAddress(), orderSelectCartDTO.getCartAddDTOS());
        return ResponseEntity.ok(orderId);
    }

    //선택한 오더를 취소
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long orderId) {
        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<Long> orderIds = orderService.getOrderIdsByMemberId(memberId);
        // 요청된 orderId가 현재 사용자의 오더 아이디 목록에 포함되어 있는지 확인합니다.
        if (orderIds.contains(orderId)) {
            // 현재 사용자의 오더이므로 삭제합니다.
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully");
        } else {
            // 요청된 orderId가 현재 사용자의 것이 아니므로 삭제 권한이 없음을 알립니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //선택한 오더를 삭제
    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<Void> removeOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long orderId) {

        Member member = orderService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<Long> orderIds = orderService.getOrderIdsByMemberId(memberId);

        // 요청된 orderId가 현재 사용자의 오더 아이디 목록에 포함되어 있는지 확인합니다.
        if (orderIds.contains(orderId)) {
            // 현재 사용자의 오더이므로 삭제합니다.
            orderService.removeOrder(orderId);
            return ResponseEntity.noContent().build();
        } else {
            // 요청된 orderId가 현재 사용자의 것이 아니므로 삭제 권한이 없음을 알립니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}