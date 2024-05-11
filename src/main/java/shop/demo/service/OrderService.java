package shop.demo.service;


import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.*;
import shop.demo.dto.OrderDTO;
import shop.demo.repository.CartRepository;
import shop.demo.repository.ItemRepository;
import shop.demo.repository.MemberRepository;
import shop.demo.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

/*
    public List<Order> viewOrdersByMemberId(Long memberId) {
        return orderRepository.findByMember(memberId);
    }*/

    public List<OrderDTO> viewOrdersByMemberId(Long memberId) {
        List<Order> orders = orderRepository.findByMember(memberId);
        return orders.stream()
                .map(order -> new OrderDTO(order.getId(),
                        order.getMember().getId(),
                        order.getAddress(),
                        order.getPayment().getId(),
                        order.getOrderItems().stream()
                                .map(OrderItem::getId)
                                .collect(Collectors.toList()),
                        order.getDate(),
                        order.getOrderState()))
                .collect(Collectors.toList());
    }


    @Transactional
    public Long addorderfromcart(Long memberId, Long cardnum) {

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
        String address = member.getAddress();
        Payment payment = createPayment(cardnum,amount);

        //주문상품 생성
        Order order = Order.createOrder(member, address, payment, orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    public List<Long> getOrderIdsByMemberId(Long memberId) {
        return orderRepository.findOrderIdsByMemberId(memberId);
    }
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            // 주문 상태를 취소로 변경합니다.
            order.setOrderState(OrderState.CANCELLED);

            // 주문된 아이템들의 재고를 복원합니다.
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                Item item = orderItem.getItem();
                int quantity = orderItem.getQuantity();
                item.addStock(quantity);
            }

            // 주문을 저장합니다.
            orderRepository.save(order);
        }
    }

    @Transactional
    public void removeOrder(Long orderid) {
        Order order = orderRepository.findById(orderid);
        orderRepository.delete(order);
    }

    public Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    private Payment createPayment(Long cardnum ,Long amount) {

        Payment payment = new Payment();
        payment.setCardnumber(cardnum);
        payment.setAmountpay(amount);
        payment.setDate(LocalDateTime.now());
        return payment;
    }
}
