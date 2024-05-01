package shop.demo.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import shop.demo.domain.Cart;
import shop.demo.domain.Member;

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

    public void flush() {em.flush();}


    public Cart findById(Long id) {return em.find(Cart.class, id);}

    public List<Cart> findBymember(Long memberId){
        Member member = em.find(Member.class, memberId);
        return member.getCarts();
    }

    public void deleteCustomerCart(Member member) {
        String hql = "delete from Cart where member = :member";
        em.createQuery(hql).setParameter("member", member).executeUpdate();
    }




}
