package com.pf.projectboard.service;

import com.pf.projectboard.domain.Article;
import com.pf.projectboard.domain.UserAccount;
import com.pf.projectboard.dto.UserAccountDto;
import com.pf.projectboard.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 회원")
@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks private UserAccountService sut;

    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("1. 회원정보를 입력 후 회원가입 - 정상 호출")
    @Test
    void givenUserAccountInfo_whenSavingUserAccount_thenSavesUserAccount() {
        //given
        UserAccountDto accountDto = createdUserAccountDto();
        given(userAccountRepository.save(any(UserAccount.class))).willReturn(createdUserAccount());

        //when
        sut.saveUserAccount(accountDto);

        // then
        then(userAccountRepository).should().save(any(UserAccount.class));

    }

    @DisplayName("2. 회원 수정 정보를 입력하면, 회원정보 업데이트")
    @Test
    void givenUserAccountInfo_whenUpdatingUserAccount_thenUpdatesUserAccount() {
        //given
        UserAccount userAccount = createdUserAccount();
        UserAccountDto dto = createdUserAccountDto("updatedPw", "updated@mail.com", "updatedNickname","updatedMemo");
        given(userAccountRepository.getReferenceById(userAccount.getUserId())).willReturn(userAccount);

        //when
        sut.updateUserAccount(userAccount.getUserId(), dto);

        //then
        assertThat(userAccount)
                .hasFieldOrPropertyWithValue("userPassword", dto.userPassword())
                .hasFieldOrPropertyWithValue("email", dto.email())
                .hasFieldOrPropertyWithValue("nickname", dto.nickname())
                .hasFieldOrPropertyWithValue("memo", dto.memo());
        then(userAccountRepository).should().getReferenceById(dto.userId());

    }

    @DisplayName("3. 회원 아이디가 주어지면, 회원 탈퇴")
    @Test
    void givenUserId_whenDeletingUserAccount_thenDeletesUserAccount() {
        //given
        String userId = "testId";
        willDoNothing().given(userAccountRepository).deleteById(userId);

        //when
        sut.deleteUserAccount(userId);

        //then
        then(userAccountRepository).should().deleteById(userId);

    }

    @DisplayName("4. 회원 ID와 비밀번호가 주어지면, 해당하는 회원의 정보를 가져온다.")
    @Test
    void givenUserIdAndUserPassword_whenResponsingUserInfo_thenResponseUserInfo() {
        //given
        UserAccount userAccount = createdUserAccount();
        String userId = "testId";
        String password = "testPw";
        given(userAccountRepository.findById(userId)).willReturn(Optional.of(userAccount));

        System.out.println("userAccount = " + userAccount);

        //when & then
        UserAccountDto dto = sut.getUserAccountInfo(userId);
        assertThat(dto)
                .hasFieldOrPropertyWithValue("userId", userAccount.getUserId())
                .hasFieldOrPropertyWithValue("userPassword", userAccount.getUserPassword())
                .hasFieldOrPropertyWithValue("email", userAccount.getEmail())
                .hasFieldOrPropertyWithValue("nickname", userAccount.getNickname())
                .hasFieldOrPropertyWithValue("memo", userAccount.getMemo());
        then(userAccountRepository).should().findById(userId);

    }

    private UserAccount createdUserAccount() {
        return UserAccount.of(
                "testId",
                "testPw",
                "test@email.com",
                "testNick",
                "testMemo"
        );
    }

    private UserAccountDto createdUserAccountDto() {
        return UserAccountDto.of(
                "testId",
                "testPw",
                "test@email.com",
                "testNick",
                "testMemo"
        );
    }

    private UserAccountDto createdUserAccountDto(String pw, String email, String nickname, String memo) {
        return UserAccountDto.of(
                "testId",
                pw, email, nickname, memo
        );
    }

}