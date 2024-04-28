package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Cart;
import shop.demo.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
/*
    @GetMapping("/viewAll")
    public ResponseEntity<List<Cart>> viewAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        List<Cart> carts = cartService.ViewAllCart(memberId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addCart(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestParam Long itemId,
                                        @RequestParam Long quantity) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        Long cartId = cartService.addCart(memberId, itemId, quantity);
        return ResponseEntity.ok(cartId);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/viewAll")
    public ResponseEntity<List<Cart>> viewAllCart(Long memberId) {
        List<Cart> carts = cartService.ViewAllCart(memberId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addCart(Long memberId,
                                        @RequestParam Long itemId,
                                        @RequestParam Long quantity) {
        Long cartId = cartService.addCart(memberId, itemId, quantity);
        return ResponseEntity.ok(cartId);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCart(Long memberId) {
        cartService.deleteAllCart(memberId);
        return ResponseEntity.noContent().build();
    }

}