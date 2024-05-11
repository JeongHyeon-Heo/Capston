package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.demo.domain.Member;
import shop.demo.dto.CustomUserDetails;
import shop.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        //DB에서 조회
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(member);
        }

        return null;
    }
}
/*
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail()) // 여기서 username은 이메일로 설정
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .build();
    }
}*/
