package com.fitcard.domain.card.benefit.service;

import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitGetSimpleResponses;
import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitResponse;

public interface CardBenefitService {

    int createCardBenefitsFromFinancial();
    CardBenefitResponse getCardBenefits(int cardId, int level);

    CardBenefitGetSimpleResponses getSimpleCardBenefits(int cardPerformanceId, int num);
}
