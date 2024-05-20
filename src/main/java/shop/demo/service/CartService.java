package shop.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import shop.demo.domain.Cart;
import shop.demo.domain.Item;
import shop.demo.domain.Member;
import shop.demo.dto.CartAddDTO;
import shop.demo.dto.CartDTO;
import shop.demo.dto.CartItemDTO;
import shop.demo.exception.ItemAlreadyInCartException;
import shop.demo.repository.CartRepository;
import shop.demo.repository.ItemRepository;
import shop.demo.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {


    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;


    public Long addCart(Long MemberID, CartAddDTO cartAddDTO){

        boolean itemExists = cartRepository.existsByMemberIdAndItemId(MemberID, cartAddDTO.getItemId());

        if (itemExists) {
            throw new ItemAlreadyInCartException("Item already in cart");
        }

        Member member = memberRepository.findOne(MemberID);
        Item item = itemRepository.findItemById(cartAddDTO.getItemId());

        Cart cart = Cart.createcart(member,item,cartAddDTO.getQuantity());

        cartRepository.save(cart);
        return cart.getId();
    }

    public void deleteCart(Long CartID){
        Cart cart = cartRepository.findById(CartID);
        cartRepository.delete(cart);
    }

    public void deleteAllCart(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        cartRepository.deleteCustomerCart(member);
    }

    /*
    public List<Cart> ViewAllCart(Long MemberID) {
        List<Cart> carts =cartRepository.findBymember(MemberID);
        return carts;
    }*/

    public List<CartDTO> viewAllCart(Long memberId) {
        List<Cart> carts = cartRepository.findBymember(memberId);
        List<CartDTO> cartDTOs = new ArrayList<>();

        for (Cart cart : carts) {
            CartDTO cartDTO = new CartDTO();

            cartDTO.setId(cart.getId());
            cartDTO.setMemberId(cart.getMember().getId());

            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setItemId(cart.getItem().getId());
            cartItemDTO.setName(cart.getItem().getName());
            cartItemDTO.setPrice(cart.getItem().getPrice());
            cartItemDTO.setCategory(cart.getItem().getCategory());
            cartItemDTO.setImageUrl(cart.getItem().getImageUrl());
            cartDTO.setCartItemDTO(cartItemDTO);

            cartDTO.setQuantity(cart.getQuantity());

            cartDTOs.add(cartDTO);
        }

        return cartDTOs;
    }
    public Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public List<Long> getUserCartIds(Long memberId) {
        return cartRepository.findCartIdsByMemberId(memberId);
    }

    @Transactional
    public void updateCartQuantity(Long memberId, Long itemId, Long newQuantity) {
        cartRepository.updateCartQuantity(memberId, itemId, newQuantity);
    }

    public Long findCartIdByMemberIdAndItemId(Long memberId, Long itemId) {
        return cartRepository.findCartIdByMemberIdAndItemId(memberId, itemId);
    }




}

