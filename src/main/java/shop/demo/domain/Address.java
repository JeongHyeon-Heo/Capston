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
/*
    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Member member;
*/
    private Long zipcode;

    private String detail;


    private Address createAddress(Long zipcode, String detail ) {
        Address address = new Address();
        address.setZipcode(zipcode);
        address.setDetail(detail);
        return address;
    }

    public String getAddressAsString() {
        return zipcode + " " + detail;
    }

}
