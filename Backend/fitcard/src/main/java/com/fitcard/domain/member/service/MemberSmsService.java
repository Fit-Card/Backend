package com.fitcard.domain.member.service;

import com.fitcard.domain.member.exception.SmsCertificationNumberMismatchException;
import com.fitcard.domain.member.model.dto.request.MemberPhoneRequest;
import com.fitcard.domain.member.model.dto.request.MemberSmsRequest;
import com.fitcard.domain.member.repository.SmsCertificationDao;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.util.SmsCertificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSmsService {

    private final SmsCertificationUtil smsUtil;
    private final SmsCertificationDao smsCertificationDao;

    public void sendSms(MemberPhoneRequest requestDto){
        String to = requestDto.getPhone();
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        String certificationNumber = String.valueOf(randomNumber);
        smsUtil.sendSms(to, certificationNumber);
        smsCertificationDao.createSmsCertification(to,certificationNumber);
    }

    public void verifySms(MemberSmsRequest requestDto) {
        System.out.println(requestDto.getCertificationNumber());
        if (isVerify(requestDto)) {
            smsCertificationDao.removeSmsCertification(requestDto.getPhone());
            throw new SmsCertificationNumberMismatchException(ErrorCode.INVALID_CERTIFICATION_NUMBER, "인증번호가 일치하지 않습니다.");
        }
    }

    public boolean isVerify(MemberSmsRequest requestDto) {
        return !(smsCertificationDao.hasKey(requestDto.getPhone()) &&
                smsCertificationDao.getSmsCertification(requestDto.getPhone())
                        .equals(requestDto.getCertificationNumber()));
    }
}
