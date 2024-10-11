package com.financial.domain.bank.cardevent.service;

import com.financial.domain.bank.cardevent.model.dto.response.BankCardEventGetResponses;

public interface BankCardEventService {
    BankCardEventGetResponses getAllEvents();
}