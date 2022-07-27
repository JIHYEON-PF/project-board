package com.pf.projectboard.service;

import com.pf.projectboard.domain.UserAccount;
import com.pf.projectboard.dto.UserAccountDto;
import com.pf.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public void saveUserAccount(UserAccountDto userAccountDto) {
        try {
            log.warn(userAccountDto.toString());
            if (userAccountDto.userId() != null || userAccountDto.userPassword() != null) {
                if (userAccountDto.nickname() == null || userAccountDto.nickname().isBlank()) {
                    userAccountDto = UserAccountDto.of(
                            userAccountDto.userId(),
                            userAccountDto.userPassword(),
                            userAccountDto.email(),
                            userAccountDto.nickname(),
                            userAccountDto.memo());
                }
                userAccountRepository.save(userAccountDto.toEntity());
            }
        } catch (EntityNotFoundException e) {
            log.warn("필수 입력정보에 누락이 있습니다. dto: {}" + userAccountDto);
        }

    }

    public void updateUserAccount(String userId, UserAccountDto dto) {

        try {
            UserAccount userAccount = userAccountRepository.getReferenceById(userId);
            if (userAccount.getUserId().equals(dto.userId())) {
                if (dto.userPassword() != null ) { userAccount.setUserPassword(dto.userPassword()); }
            }
            userAccount.setEmail(dto.email());
            userAccount.setNickname(dto.nickname());
            userAccount.setMemo(dto.memo());
//            userAccountRepository.save(userAccount);
        } catch (EntityNotFoundException e) {
            log.warn("회원정보 업데이트 실패, 관련된 회원정보를 찾을 수 없습니다. : {} " + e.getLocalizedMessage());
        }

    }

    public void deleteUserAccount(String userId) {

        userAccountRepository.deleteById(userId);

    }

    public UserAccountDto getUserAccountInfo(String userId) {

        return userAccountRepository.findById(userId)
                .map(UserAccountDto::from)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보가 없습니다."));

    }
}
