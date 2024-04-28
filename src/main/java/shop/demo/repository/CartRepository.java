package shop.demo.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import shop.demo.domain.Cart;
import shop.demo.domain.Member;
import shop.demo.domain.Order;

import java.util.List;

@Repository
public class CartRepository {
    private final EntityManager em;

    public CartRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Cart cart) {
        em.persist(cart);
    }

    public void delete(Cart cart) {em.remove(cart);}

    public Cart findById(Long id) {return em.find(Cart.class, id);}

    public List<Cart> findBymember(Long memberId){
        Member member = em.find(Member.class, memberId);
        return member.getCarts();
    }
}
