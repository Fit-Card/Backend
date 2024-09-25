package com.financial.infra.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class CrawlController {
    private final KbCardEventCrawler kbCardEventCrawler;
    private final ShCardEventCrawler shCardEventCrawler;
    private final HanaCardEventCrawler hanaCardEventCrawler;
    private final NhCardEventCrawler nhCardEventCrawler;
    private final WooriCardEventCrawler wooriCardEventCrawler;
    private final SamsungCardEventCrawler samsungCardEventCrawler;

    @Autowired
    public CrawlController(KbCardEventCrawler kbCardEventCrawler, ShCardEventCrawler shCardEventCrawler, HanaCardEventCrawler hanaCardEventCrawler, NhCardEventCrawler nhCardEventCrawler, WooriCardEventCrawler wooriCardEventCrawler, SamsungCardEventCrawler samsungCardEventCrawler) {
        this.kbCardEventCrawler = kbCardEventCrawler;
        this.shCardEventCrawler = shCardEventCrawler;
        this.hanaCardEventCrawler = hanaCardEventCrawler;
        this.nhCardEventCrawler =  nhCardEventCrawler;
        this.wooriCardEventCrawler = wooriCardEventCrawler;
        this.samsungCardEventCrawler = samsungCardEventCrawler;
    }

    @GetMapping("/kb")
    public ResponseEntity<?> crawlEventsKb() {
        kbCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링이 완료되었습니다.");  // 크롤링된 링크를 반환
    }

    @GetMapping("/sh")
    public ResponseEntity<?> crawlEventsSh() {
        shCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링이 완료되었습니다.");  // 크롤링된 링크를 반환
    }

    @GetMapping("/nh")
    public ResponseEntity<?> crawlEventsNh(){
        nhCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링 완료");
    }
    @GetMapping("/hana")
    public ResponseEntity<?> crawlEventsHana() {
        hanaCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링이 완료되었습니다.");  // 크롤링된 링크를 반환
    }

    @GetMapping("/woori")
    public ResponseEntity<?> crawlEventsWoori() {
        wooriCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링이 완료되었습니다.");  // 크롤링된 링크를 반환
    }

    @GetMapping("/samsung")
    public ResponseEntity<?> crawlEventsSamsung() {
        samsungCardEventCrawler.crawlEventLinks();
        return ResponseEntity.status(200).body("크롤링이 완료되었습니다.");  // 크롤링된 링크를 반환
    }
}
