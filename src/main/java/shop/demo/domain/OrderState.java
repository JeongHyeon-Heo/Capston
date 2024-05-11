package shop.demo.domain;

import lombok.Getter;

@Getter
public enum OrderState {
    SHIPPED, DELIVERED, CANCELLED
}
