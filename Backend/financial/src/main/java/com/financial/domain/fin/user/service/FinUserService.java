package com.financial.domain.fin.user.service;

import com.financial.domain.fin.user.model.dto.request.FinUserDeleteRequest;
import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;

public interface FinUserService {

    void createUser(FinUserSaveRequest request);

    void deleteUser(FinUserDeleteRequest request);
}
