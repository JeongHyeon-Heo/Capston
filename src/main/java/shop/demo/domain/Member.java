package shop.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime registrationDate;


    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    private List<Notification> notifications = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    private List<ServiceCenter> serviceCenters = new ArrayList<>();


    public static Member createMember(String name, String email, String password, LocalDateTime registrationDate) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setRegistrationDate(registrationDate);

        return member;
    }

}
