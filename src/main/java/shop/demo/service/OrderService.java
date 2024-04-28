package shop.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.*;
import shop.demo.repository.CartRepository;
import shop.demo.repository.ItemRepository;
import shop.demo.repository.MemberRepository;
import shop.demo.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;


    public List<Order> viewOrdersByMemberId(Long memberId) {
        return orderRepository.findByMember(memberId);
    }


    @Transactional
    public Long addorderfromcart(Long memberId, Long zipcode, String detail, Long cardnum) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        List<Cart> carts = cartRepository.findBymember(member.getId());
        List<Item> items = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        Long amount= 0L;

        for (Cart cart : carts) {
            Item item = cart.getItem();
            int quantity = (cart.getQuantity()).intValue();
            amount += item.getPrice()*quantity;
            OrderItem orderItem = OrderItem.createOrderItem(item, quantity);
            orderItems.add(orderItem);
            items.add(item);
        }

        //배송정보 생성
        Address address = createAddress(zipcode,detail);
        Payment payment = createPayment(cardnum,amount);

        //주문상품 생성


        Order order = Order.createOrder(member, address, payment, orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    public void removeOrder(Long memberID) {
        List<Order> orders = orderRepository.findByMember(memberID);
        for (Order order : orders) {
            orderRepository.delete(order);
        }
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
