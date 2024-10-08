package com.financial.domain.bank.cardevent.service;

import com.financial.domain.bank.cardevent.model.BankCardEvent;
import com.financial.domain.bank.cardevent.model.dto.response.BankCardEventGetResponse;
import com.financial.domain.bank.cardevent.model.dto.response.BankCardEventGetResponses;
import com.financial.domain.bank.cardevent.repository.BankCardEventRepository;
import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import com.financial.domain.fin.cardcompany.repository.FinCardCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankCardEventServiceImpl implements BankCardEventService {

    private final BankCardEventRepository bankCardEventRepository;

    @Override
    public BankCardEventGetResponses getAllEvents(){
        List<BankCardEventGetResponse> bankCardEventGetResponses = bankCardEventRepository.findAll().stream()
                .map(BankCardEventGetResponse::of).toList();
        return BankCardEventGetResponses.from(bankCardEventGetResponses);
    }

}