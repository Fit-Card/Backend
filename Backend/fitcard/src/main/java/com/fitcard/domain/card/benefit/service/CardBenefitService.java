package com.fitcard.domain.card.benefit.service;

import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitResponse;

public interface CardBenefitService {

    int createCardBenefitsFromFinancial();
    CardBenefitResponse getCardBenefits(int cardVersionId, int level);
}
