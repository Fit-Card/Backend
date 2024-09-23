package com.fitcard.domain.card.cardinfo.service;

import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponses;

public interface CardInfoService {
    CardInfoGetResponses getCardsByCompany(int cardCompanyId);
}
