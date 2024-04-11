package shop.demo.service;

import org.springframework.stereotype.Service;
import shop.demo.repository.MemberRepository;
import shop.demo.domain.Member;

import java.util.List;

@Service

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Member findMemberById(Long id) {
        return memberRepository.findOne(id);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }
}
