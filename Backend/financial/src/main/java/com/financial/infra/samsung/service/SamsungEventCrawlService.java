package com.financial.infra.samsung.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SamsungEventCrawlService {

    private final String SAMSUNG_EVENT_MAIN;
    private final String SAMSUNG_EVENT_DETAIL;

    public SamsungEventCrawlService(@Value("${crawl.samsung.event.main}") String SAMSUNG_EVENT_MAIN,
                                    @Value("${crawl.samsung.event.detail}") String SAMSUNG_EVENT_DETAIL) {
        this.SAMSUNG_EVENT_MAIN = SAMSUNG_EVENT_MAIN;
        this.SAMSUNG_EVENT_DETAIL = SAMSUNG_EVENT_DETAIL;
    }

    //


}
