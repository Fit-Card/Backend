//package com.financial.domain.bank.cardevent.service;
//
//import com.financial.domain.bank.cardevent.model.BankCardEvent;
//import com.financial.domain.bank.cardevent.repository.BankCardEventRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class BankCardEventServiceImpl implements BankCardEventService {
//
//    private final BankCardEventRepository bankCardEventRepository;
//
//    @Override
//    public void createBankCardEvent() {
//        BankCardEvent bankCardEvent = new BankCardEvent();
//        bankCardEventRepository.save(bankCardEvent);
//    }
//}
