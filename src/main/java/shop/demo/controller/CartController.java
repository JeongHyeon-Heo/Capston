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
import shop.demo.service.CartService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/viewAll")
    public ResponseEntity<List<CartDTO>> viewAllCart(@AuthenticationPrincipal UserDetails userDetails) {


        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        List<CartDTO> cartDTOs = cartService.viewAllCart(memberId);
        return ResponseEntity.ok(cartDTOs);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addCart(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody CartAddDTO cartAddDTO) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        Long cartId = cartService.addCart(memberId, cartAddDTO);
        return ResponseEntity.ok(cartId);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long cartId) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();

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

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = cartService.findMemberByEmail(userDetails.getUsername());
        Long memberId = member.getId();
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }
/*
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }*/

    /*@GetMapping("/viewAll/{memberId}")
    public ResponseEntity<List<Cart>> viewAllCart(@PathVariable Long memberId) {
        List<Cart> carts = cartService.ViewAllCart(memberId);
        return ResponseEntity.ok(carts);
    }*/
    /*
    @GetMapping("/viewAll/{memberId}")
    public ResponseEntity<List<CartDTO>> viewAllCart(@PathVariable Long memberId) {
        List<CartDTO> cartDTOs = cartService.viewAllCart(memberId);
        return ResponseEntity.ok(cartDTOs);
    }*/

    /*
    @PostMapping("/add/{memberId}")
    public ResponseEntity<Long> addCart(@PathVariable Long memberId,
                                        @RequestParam Long itemId,
                                        @RequestParam Long quantity) {
        Long cartId = cartService.addCart(memberId, itemId, quantity);
        return ResponseEntity.ok(cartId);
    }*/

    /*
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }*/
    /*
    @DeleteMapping("/deleteAll/{memberId}")
    public ResponseEntity<Void> deleteAllCart(@PathVariable Long memberId) {
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }*/



}