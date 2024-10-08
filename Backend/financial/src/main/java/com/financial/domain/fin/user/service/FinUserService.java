package com.financial.domain.fin.user.service;

import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserGetIdRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.domain.fin.user.model.dto.response.FinUserGetIdResponse;

public interface FinUserService {

    void createUser(FinUserSaveRequest request);

    void deleteUser(FinUserDeleteRequest request);

    FinUserGetIdResponse getFinUserId(FinUserGetIdRequest request);

}
