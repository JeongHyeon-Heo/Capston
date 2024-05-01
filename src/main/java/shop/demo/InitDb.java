package shop.demo;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = createMember("userA");
            em.persist(member);

            Item cloth1 = createItem();
            Item cloth2 = createItem();
            em.persist(cloth1);
            em.persist(cloth2);


            Cart cart1 = Cart.createcart(member,cloth1, 1L);
            Cart cart2 = Cart.createcart(member,cloth2, 1L);

            OrderItem orderItem1 = OrderItem.createOrderItem(cloth1, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(cloth2, 2);
            OrderItem orderItem3 = OrderItem.createOrderItem(cloth2, 2);

            List<OrderItem> orderItems1 = new ArrayList<>();
            orderItems1.add(orderItem1);
            orderItems1.add(orderItem2);

            List<OrderItem> orderItems2 = new ArrayList<>();
            orderItems2.add(orderItem3);


            Address address1 = createAddress(1L,"주소1");
            Address address2 = createAddress(1L,"주소1");
            Address address3 = createAddress(1L,"주소1");
            Payment payment1 = createPayment(1L,1000L);
            Payment payment2 = createPayment(1L,1000L);
            Payment payment3 = createPayment(1L,1000L);

            Order order1 = Order.createOrder(member, address1 ,payment1 ,orderItems1);
            Order order2 = Order.createOrder(member, address2 ,payment2 ,orderItems2);


            em.persist(order1);
            em.persist(order2);
        }



        private Member createMember(String name) {
            Member member = new Member();
            member.setName(name);
            member.setDate(LocalDateTime.now());
            member.setEmail("hansung.ac.kr");
            member.setPassword("1234");
            return member;
        }

        private Item createItem(){
            Item item = new Item();
            item.setName("상의1");
            item.setPrice(1000);
            item.setStockQuantity(3);
            //item.setCategory("");
            return item;
        }

            private Address createAddress(Long zipcode, String detail ) {
                Address address = new Address();
                address.setZipcode(zipcode);
                address.setDetail(detail);
                return address;
            }

            private Payment createPayment(Long cardnum ,Long amount) {

                Payment payment = new Payment();
                payment.setCardnumber(cardnum);
                payment.setAmountpay(amount);
                payment.setDate(LocalDateTime.now());
                return payment;
            }




    }
}
