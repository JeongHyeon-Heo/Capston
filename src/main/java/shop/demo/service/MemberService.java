package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.Address;
import shop.demo.domain.Cart;
import shop.demo.domain.Order;
import shop.demo.dto.MemberDTO;
import shop.demo.dto.MemberInfoDTO;
import shop.demo.repository.MemberRepository;
import shop.demo.domain.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


/*
    @Transactional
    public void saveMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        member.setAddress(memberDTO.getAddress());
        member.setRegistrationDate(LocalDateTime.now());
        memberRepository.save(member);
    }*/

    @Transactional
    public void saveMember(MemberDTO memberDTO){
        Member member = Member.createMember(memberDTO, passwordEncoder);
        memberRepository.save(member);
    }


//    public Member findMemberById(Long id) {
//        return memberRepository.findOne(id);
//    }
public MemberInfoDTO findMemberById(Long id) {
    Member member = memberRepository.findOne(id);

    /* 수정 */
    if (member != null) {
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        List<Long> orderIds = member.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        memberInfoDTO.setOrders(orderIds);
        List<Long> cartIds = member.getCarts().stream().map(Cart::getId).collect(Collectors.toList());
        memberInfoDTO.setCarts(cartIds);
/* 수정 */
//        memberInfoDTO.setOrders(member.getOrders());
//        memberInfoDTO.setCarts(member.getCarts());
        memberInfoDTO.setName(member.getName());
        memberInfoDTO.setEmail(member.getEmail());
        memberInfoDTO.setDate(member.getRegistrationDate());
        return memberInfoDTO;
    } else {
        return null; // 또는 예외 처리를 수행할 수도 있습니다.
    }
}

//    public List<Member> findAllMembers() {
//        return memberRepository.findAll();
//    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional
    public boolean deleteMemberById(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
