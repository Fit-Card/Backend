package com.financial.domain.fin.user.service;

import com.financial.domain.fin.user.model.FinUser;
import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.domain.fin.user.repository.FinUserRepository;
import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FinUserServiceImpl implements FinUserService{

    private final FinUserRepository finUserRepository;

    @Override
    public void createUser(FinUserSaveRequest request) {
        FinUser finUser = FinUser.from(request);
        log.info("user: {}", finUser);
        finUserRepository.save(finUser);
    }

    @Override
    public void deleteUser(FinUserDeleteRequest request) {
        FinUser finUser = finUserRepository.findById(request.getId()).orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "존재하지 않는 사용자입니다."));
        finUserRepository.delete(finUser);
    }
}
