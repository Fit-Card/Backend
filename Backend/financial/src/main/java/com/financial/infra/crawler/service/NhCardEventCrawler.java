package com.financial.infra.crawler.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NhCardEventCrawler {
    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.nh}")
    private String eventUrl;

    public void crawlEventLinks() {

    }
}
