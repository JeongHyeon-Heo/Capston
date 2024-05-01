package shop.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;


    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Order order;

    private Long zipcode;

    private String detail;



}
