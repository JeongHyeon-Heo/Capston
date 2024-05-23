package shop.demo.domain;

import lombok.Getter;

@Getter
public enum OrderState {
    PREPARING ,SHIPPED, DELIVERED, CANCELLED
}
