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

            Item cloth = createItem();
            em.persist(cloth);



            OrderItem orderItem1 = OrderItem.createOrderItem(cloth, 1);

            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem1);

            Address address = createAddress(1L,"주소1");
            Payment payment = createPayment(1L,1000L);

            Order order = Order.createOrder(member, address,payment ,orderItems);



            em.persist(order);
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
