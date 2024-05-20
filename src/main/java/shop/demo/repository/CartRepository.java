package shop.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public List<Long> findCartIdsByMemberId(Long memberId) {
        return em.createQuery("SELECT c.id FROM Cart c WHERE c.member.id = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void deleteCustomerCart(Member member) {
        String hql = "delete from Cart where member = :member";
        em.createQuery(hql).setParameter("member", member).executeUpdate();
    }

    public boolean existsByMemberIdAndItemId(Long memberId, Long itemId) {
        String jpql = "SELECT COUNT(c) FROM Cart c WHERE c.member.id = :memberId AND c.item.id = :itemId";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("memberId", memberId);
        query.setParameter("itemId", itemId);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public void updateCartQuantity(Long memberId, Long itemId, Long newQuantity) {
        em.createQuery("UPDATE Cart c SET c.quantity = :newQuantity WHERE c.member.id = :memberId AND c.item.id = :itemId")
                .setParameter("newQuantity", newQuantity)
                .setParameter("memberId", memberId)
                .setParameter("itemId", itemId)
                .executeUpdate();
    }

    public Long findCartIdByMemberIdAndItemId(Long memberId, Long itemId) {
        return em.createQuery(
                        "SELECT c.id FROM Cart c WHERE c.member.id = :memberId AND c.item.id = :itemId", Long.class)
                .setParameter("memberId", memberId)
                .setParameter("itemId", itemId)
                .getSingleResult();
    }




}
