package com.financial.domain.bank.cardevent.service;

import com.financial.domain.bank.cardevent.model.BankCardEvent;
import com.financial.domain.bank.cardevent.repository.BankCardEventRepository;
import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import com.financial.domain.fin.cardcompany.repository.FinCardCompanyRepository;
import com.financial.infra.crawler.service.KbCardEventCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankCardEventServiceImpl implements BankCardEventService {

    private final BankCardEventRepository bankCardEventRepository;
    private final FinCardCompanyRepository finCardCompanyRepository;
    private final KbCardEventCrawler kbCardEventCrawler;

    @Override
    public void createBankCardEvent(String bankCardId) {
        FinCardCompany finCardCompany = finCardCompanyRepository.findById(bankCardId).orElseThrow(() -> new NotFoundException("없음"));
        Map<String, String[]> map = kbCardEventCrawler.crawlEventLinks();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // map의 데이터를 BankCardEvent로 변환하여 저장
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String eventUrl = entry.getKey();
            String[] eventData = entry.getValue();

            String title = eventData[0];
            LocalDate startDate = LocalDate.parse(eventData[1], formatter);  // 필요한 형식에 맞게 파싱
            LocalDate endDate = LocalDate.parse(eventData[2], formatter);    // 필요한 형식에 맞게 파싱
            String content = eventData[3];
            String target = eventData[4];

            // BankCardEvent 엔티티 생성
            BankCardEvent cardEvent = BankCardEvent.of(
                    finCardCompany,         // 관련된 FinCardCompany
                    target,           // 대상
                    true,             // isCategory는 일단 true로 설정
                    eventUrl,         // 이벤트 URL
                    startDate,        // 이벤트 시작일
                    endDate,          // 이벤트 종료일
                    title,            // 이벤트 제목
                    content           // 이벤트 내용
            );

            // 데이터베이스에 저장
            bankCardEventRepository.save(cardEvent);
            log.info("이벤트 저장 완료: {}", title);
        }

    }
}
