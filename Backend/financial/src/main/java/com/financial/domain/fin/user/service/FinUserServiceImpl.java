package com.financial.domain.fin.user.service;

import com.financial.domain.fin.user.exception.FinUserGetIdException;
import com.financial.domain.fin.user.model.FinUser;
import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserGetIdRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.domain.fin.user.model.dto.response.FinUserGetIdResponse;
import com.financial.domain.fin.user.repository.FinUserCertificationDao;
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
    private final FinUserCertificationDao finUserCertificationDao;

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

    @Override
    public FinUserGetIdResponse getFinUserId(FinUserGetIdRequest request) {
        FinUser finUser = finUserRepository.findByPhoneNumber(request.getPhone())
                .orElseThrow(() -> new FinUserGetIdException(ErrorCode.BAD_REQUEST, "해당 사용자가 없습니다."));
//        log.info("verifyCode: {}", finUserCertificationDao.getSmsCertification(request.getPhone()));
//        log.info("input code: {}", request.getVerificationCode());
        if(!isVerify(request)){
            throw new FinUserGetIdException(ErrorCode.BAD_REQUEST, "인증번호가 일치하지 않습니다.");
        }
        return FinUserGetIdResponse.of(finUser);
    }

    public boolean isVerify(FinUserGetIdRequest request) {
        return (finUserCertificationDao.hasKey(request.getPhone()) &&
                finUserCertificationDao.getSmsCertification(request.getPhone())
                        .equals(request.getVerificationCode()));
    }
}
