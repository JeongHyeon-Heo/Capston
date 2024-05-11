package shop.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.demo.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @Enumerated(EnumType.STRING)
    private Role role;
/*
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;*/
    private String address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ServiceCenter> serviceCenters = new ArrayList<>();

/*
    public void setAddress(Address address) {
        this.address = address;
        address.setMember(this);
    }*/


    public static Member createMember(MemberDTO memberFormDto, PasswordEncoder passwordEncoder ) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setRegistrationDate(LocalDateTime.now());
        String password =  passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ROLE_USER); // 계정 생성 시 권한을 USER으로 고정

        return member;
    }

    public void setRoleFromString(String role) {
        // Enum.valueOf 메서드를 사용하여 문자열을 Enum으로 변환
        this.role = Role.valueOf(role.toUpperCase());
    }



}
