package com.parkjinhun.kmong.kmong_assignment_project.domain.member;

import com.parkjinhun.kmong.kmong_assignment_project.common.util.HashGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterMember {
        private final String memberId;
        private final String memberEmail;
        private final String memberPassword;

        public Member toEntity() {
            return Member.builder()
                    .memberId(memberId)
                    .memberEmail(memberEmail)
                    .memberPassword(HashGenerator.passwordEncoder(memberPassword))
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class LoginMember {
        private final String memberId;
        private final String memberPassword;

        public LoginMember(String memberId, String memberPassword) {
            this.memberId = memberId;
            this.memberPassword = memberPassword;
        }

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(memberId, memberPassword);
        }
    }


}