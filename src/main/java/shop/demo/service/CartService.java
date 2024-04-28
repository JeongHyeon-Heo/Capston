package shop.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.demo.domain.Cart;
import shop.demo.domain.Item;
import shop.demo.domain.Member;
import shop.demo.repository.CartRepository;
import shop.demo.repository.ItemRepository;
import shop.demo.repository.MemberRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;


    public Long addCart(Long MemberID, Long ItemID, Long quantity){

        Member member = memberRepository.findOne(MemberID);
        Item item = itemRepository.findItemById(ItemID);

        Cart cart = Cart.createcart(member,item,quantity);

        cartRepository.save(cart);
        return cart.getId();
    }

    public void deleteCart(Long CartID){
        Cart cart = cartRepository.findById(CartID);
        cartRepository.delete(cart);
    }
    public void deleteAllCart(Long MemberID){

        List<Cart> carts =cartRepository.findBymember(MemberID);
        for (Cart cart : carts) {
            cartRepository.delete(cart);
        }

    }

    public List<Cart> ViewAllCart(Long MemberID) {
        List<Cart> carts =cartRepository.findBymember(MemberID);
        return carts;
    }




}
