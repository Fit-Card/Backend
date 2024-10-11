package com.fitcard.domain.membercard.performance.service;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;

import java.util.List;

public interface MemberCardPerformanceService {

    MemberCardPerformance createMemberCardPerformance(MemberCardInfo memberCardInfo, List<Payment> payments, int year, int month);
}
