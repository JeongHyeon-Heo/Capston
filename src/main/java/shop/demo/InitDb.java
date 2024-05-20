package shop.demo;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.*;
import shop.demo.dto.MemberDTO;

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
        private final PasswordEncoder passwordEncoder;
        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = Member.createMember(createTestMemberDTO(), passwordEncoder);
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
            String address1_2 = address1.getAddressAsString();
            Address address2 = createAddress(1L,"주소1");
            String address2_2 = address2.getAddressAsString();
            Address address3 = createAddress(1L,"주소1");

            long cardnum = 123456789L;
            long amountpay = 100000L;
            Order order1 = Order.createOrder(member, address1_2 ,cardnum, amountpay ,orderItems1);
            Order order2 = Order.createOrder(member, address2_2 ,cardnum, amountpay ,orderItems2);


            em.persist(order1);
            em.persist(order2);
        }



        private Member createMember(String name) {
            Member member = new Member();
            member.setName(name);

            member.setRegistrationDate(LocalDateTime.now());
            member.setEmail("hansung.ac.kr");
            member.setPassword("1234");
            return member;
        }

        public MemberDTO createTestMemberDTO() {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setName("Test User");
            memberDTO.setEmail("hansung@hansung.ac.kr");
            memberDTO.setPassword("1234");
            memberDTO.setAddress("Test Address");
            memberDTO.setDate(LocalDateTime.now());
            return memberDTO;
        }

        private Item createItem(){
            Item item = new Item();
            item.setName("상의1");
            item.setPrice(1000);
            item.setStockQuantity(10);
            item.setCategory(Category.valueOf("TOP"));
            return item;
        }

            private Address createAddress(Long zipcode, String detail ) {
                Address address = new Address();
                address.setZipcode(zipcode);
                address.setDetail(detail);
                return address;
            }
/*
            private Payment createPayment(Long cardnum ,Long amount) {

                Payment payment = new Payment();
                payment.setCardnumber(cardnum);
                payment.setAmountpay(amount);
                payment.setDate(LocalDateTime.now());
                return payment;
            }*/




    }
}

