package com.pf.projectboard.controller;

import com.pf.projectboard.dto.UserAccountDto;
import com.pf.projectboard.dto.request.UserAccountRequest;
import com.pf.projectboard.dto.response.UserAccountResponse;
import com.pf.projectboard.dto.security.BoardPrincipal;
import com.pf.projectboard.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserAccountController {

    private final UserAccountService userAccountService;

    //회원 정보를 불러온다.(마이페이지에서 사용)
    @GetMapping("/{userId}")
    public String showUserAccount(@PathVariable String userId, ModelMap map) {

        UserAccountResponse userAccount = UserAccountResponse.from(userAccountService.getUserAccountInfo(userId));
        map.addAttribute("userAccount", userAccount);
        log.info(userAccount.toString());
        System.out.println("userAccount = " + userAccount);

        return "mypage";
    }

    //회원 정보를 수정한다.(마이페이지에서 사용)

    //회원가입 페이지로 이동
    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    //회원정보를 등록한다.(회원가입 페이지에서 사용)
    @PostMapping("/signUp")
    public String signUp(
            UserAccountRequest userAccountRequest
        ) {
        userAccountService.saveUserAccount(userAccountRequest.toDto());

        return "redirect:/";
    }

}
