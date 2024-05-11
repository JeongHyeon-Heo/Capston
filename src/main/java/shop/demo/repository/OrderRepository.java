package shop.demo.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shop.demo.domain.Member;
import shop.demo.domain.Order;

import java.util.List;

@Repository
public class OrderRepository {
    private final EntityManager em;


    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public void delete(Order order) {em.remove(order);}

    public Order findById(Long id) {return em.find(Order.class, id);}

    public List<Order> findByMember(Long memberId){
        Member member = em.find(Member.class, memberId);
        return member.getOrders();
    }

    public List<Long> findOrderIdsByMemberId(Long memberId) {
        return em.createQuery("SELECT o.id FROM Order o WHERE o.member.id = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }


}