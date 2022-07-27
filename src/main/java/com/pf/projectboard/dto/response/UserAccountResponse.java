package com.pf.projectboard.dto.response;

import com.pf.projectboard.domain.UserAccount;
import com.pf.projectboard.dto.UserAccountDto;

public record UserAccountResponse(
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo
) {

    public static UserAccountResponse of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccountResponse(userId,
                userPassword,
                email,
                nickname,
                memo);
    }

    public static UserAccountResponse from(UserAccountDto dto) {
        return UserAccountResponse.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId, userPassword, email, nickname, memo
        );
    }



}
