package shop.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;


    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Order order;

    private Long cardnumber;

    private Long amountpay;

    private LocalDateTime date;
}
