package com.pf.projectboard.controller;

import com.pf.projectboard.config.TestSecurityConfig;
import com.pf.projectboard.domain.UserAccount;
import com.pf.projectboard.dto.UserAccountDto;
import com.pf.projectboard.dto.response.UserAccountResponse;
import com.pf.projectboard.service.UserAccountService;
import com.pf.projectboard.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - 회원")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(UserAccountController.class)
class UserAccountControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private UserAccountService userAccountService;

    UserAccountControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @WithMockUser
    @DisplayName("1. 회원 마이페이지 호출 - 정상호출")
    @Test
    void givenNothing_whenRequestingMyPage_thenReturnsMyPage() throws Exception {
        //given
        String userId = "testId";
        UserAccountDto dto = createdUserAccountDto();
        given(userAccountService.getUserAccountInfo(userId)).willReturn(dto);

        //when & then
        mvc.perform(get("/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("mypage"))
                .andExpect(model().attribute("userAccount", UserAccountResponse.from(dto)));
        then(userAccountService).should().getUserAccountInfo(userId);

    }

    @DisplayName("2. 회원가입 페이지 호출 - 정상 호출")
    @Test
    void givenNothing_whenRequestingSignUpView_ReturnsSignUpView() throws Exception {
        //given


        //when & then
        mvc.perform(get("/user/signUp"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("signUp"));

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