package com.fitcard.domain.card.cardinfo.service;

import com.fitcard.domain.card.cardinfo.exception.GetCardsByCompanyException;
import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponse;
import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponses;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CardInfoServiceImpl implements CardInfoService {

    private final CardInfoRepository cardInfoRepository;
    private final CardCompanyRepository cardCompanyRepository;

    @Override
    public CardInfoGetResponses getCardsByCompany(int cardCompanyId) {

        CardCompany cardCompany = cardCompanyRepository.findById(cardCompanyId).orElseThrow(
                () -> new GetCardsByCompanyException(ErrorCode.CARD_COMPANY_NOT_FOUND, "해당하는 카드사가 없습니다."));
        List<CardInfoGetResponse> cardInfoGetResponses = cardInfoRepository.findByCardCompany(cardCompany).stream()
                .map(CardInfoGetResponse::of)
                .toList();

        return CardInfoGetResponses.from(cardInfoGetResponses);
    }
}
