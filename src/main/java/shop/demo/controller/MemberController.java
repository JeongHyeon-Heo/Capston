package shop.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.demo.service.MemberService;
import shop.demo.domain.Member;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/add")
    public String addMember(@RequestBody Member member) {
        memberService.saveMember(member);
        return "add success";
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @GetMapping("/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/search")
    public List<Member> searchMembersByName(@RequestParam String name) {
        return memberService.findMembersByName(name);
    }


}
