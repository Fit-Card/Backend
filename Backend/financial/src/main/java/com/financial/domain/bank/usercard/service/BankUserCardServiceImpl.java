package com.financial.domain.bank.usercard.service;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.card.repository.BankCardRepository;
import com.financial.domain.bank.usercard.exception.BankUserCardGetAllUserCardsException;
import com.financial.domain.bank.usercard.exception.DeleteUserCardException;
import com.financial.domain.bank.usercard.exception.SaveUserCardException;
import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardDeleteRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponse;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponses;
import com.financial.domain.bank.usercard.repository.BankUserCardRepository;
import com.financial.domain.fin.user.model.FinUser;
import com.financial.domain.fin.user.repository.FinUserRepository;
import com.financial.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankUserCardServiceImpl implements BankUserCardService {

    private final BankUserCardRepository bankUserCardRepository;
    private final BankCardRepository bankCardRepository;
    private final FinUserRepository finUserRepository;

    @Override
    public void createUserCard(BankUserCardSaveRequest request) {
        BankCard bankCard = bankCardRepository.findById(request.getBankCardId())
                .orElseThrow(() -> new SaveUserCardException(ErrorCode.BAD_REQUEST, "카드가 존재하지 않습니다"));
        FinUser finUser = finUserRepository.findById(request.getFinUserId())
                .orElseThrow(() -> new SaveUserCardException(ErrorCode.BAD_REQUEST, "사용자가 존재하지 않습니다"));
        if(bankUserCardRepository.existsByFinUserAndBankCard(finUser, bankCard)){
            throw new SaveUserCardException(ErrorCode.BAD_REQUEST, "이미 존재하는 카드입니다");
        }
        bankUserCardRepository.save(BankUserCard.of(request, bankCard, finUser));
    }

    @Override
    public void deleteUserCard(BankUserCardDeleteRequest request) {
        BankUserCard bankUserCard = bankUserCardRepository.findById(request.getBankUserCardId())
                .orElseThrow(() -> new DeleteUserCardException(ErrorCode.BAD_REQUEST, "사용자 카드가 존재하지 않습니다"));
        bankUserCardRepository.delete(bankUserCard);
    }

    @Override
    public BankUserCardGetResponses getAllUserCards(BankUserCardGetRequest request) {
        FinUser finUser = finUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new BankUserCardGetAllUserCardsException(ErrorCode.BAD_REQUEST, "사용자가 존재하지 않습니다."));
        List<BankUserCardGetResponse> bankUserCardGetResponses = bankUserCardRepository.findByFinUser(finUser).stream()
                .map(BankUserCardGetResponse::from)
                .toList();
        return BankUserCardGetResponses.from(bankUserCardGetResponses);
    }
}
