package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.repository.MemberRepository;
import shop.demo.domain.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

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
    public boolean deleteMemberById(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
