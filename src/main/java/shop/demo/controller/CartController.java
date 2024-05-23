package shop.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Cart;
import shop.demo.domain.Member;
import shop.demo.dto.CartAddDTO;
import shop.demo.dto.CartDTO;
import shop.demo.exception.ItemAlreadyInCartException;
import shop.demo.service.CartService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //현재 맴버의 카트를 모두 조회
    @GetMapping("/viewAll")
    public ResponseEntity<List<CartDTO>> viewAllCart(@AuthenticationPrincipal UserDetails userDetails) {


        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<CartDTO> cartDTOs = cartService.viewAllCart(memberId);
        return ResponseEntity.ok(cartDTOs);
    }

    //선택한 아이템을 카트에 추가
    @PostMapping("/add")
    public ResponseEntity<Object> addCart(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody CartAddDTO cartAddDTO) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        try {
            Long cartId = cartService.addCart(memberId, cartAddDTO);
            return ResponseEntity.ok(cartId);
        } catch (ItemAlreadyInCartException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Item already in cart");
        }

    }

    //카트에 있는 아이템의 수량을 조절
    @PutMapping("/quantity")
    public ResponseEntity<String> updateCartQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody CartAddDTO cartAddDTO) {

        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        try {
            cartService.updateCartQuantity(memberId, cartAddDTO.getItemId(), cartAddDTO.getQuantity());
            return ResponseEntity.ok("Cart quantity updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cart quantity.");
        }
    }

    //카트에 있는 아이템을 삭제
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long itemId) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long cartId = cartService.findCartIdByMemberIdAndItemId(memberId, itemId);

        List<Long> userCartIds = cartService.getUserCartIds(memberId);
        if (userCartIds.contains(cartId)) {
            // 현재 사용자의 카트이므로 삭제합니다.
            cartService.deleteCart(cartId);
            return ResponseEntity.noContent().build();
        } else {
            // 요청된 카트 ID가 현재 사용자의 것이 아니므로 삭제 권한이 없음을 알립니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //카트에 있는 내용을 모두 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }


}