package com.financial.infra.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShCardEventCrawler {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.sh}")
    private String eventUrl;

    public void crawlEventLinks() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();

        // 첫 번째 페이지로 이동
        driver.get(eventUrl);

        Map<String, String> map = new HashMap<>();

        // 페이지 소스를 가져와 Jsoup으로 파싱
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        // 이벤트 링크를 추출 (div id="evtList" > ul id="listData" > li class="list-item" > a)
        doc.select("div#evtList ul#listData li.list-item a").forEach(element -> {
            String eventLink = element.attr("href");

            // 이벤트 상세 링크에 전체 URL을 붙여서 생성
            String fullEventUrl = "https://www.shinhancard.com" + eventLink;

            String title = element.select("div.text-sec div.text1").text();
            System.out.println("URL: " + fullEventUrl);
            System.out.println("title: " + title);

            // map에 URL을 key로 저장 (value는 아직 추가하지 않음)
            map.put(fullEventUrl, "");  // value는 나중에 상세 데이터를 추가할 때 사용
        });

        for (String detailUrl : map.keySet()) {
            driver.get(detailUrl);  // 상세 페이지로 이동

            // 페이지 소스를 가져와 Jsoup으로 파싱
            String detailPageSource = driver.getPageSource();
            Document detailDoc = Jsoup.parse(detailPageSource);

            Elements columns = detailDoc.select("div.column");
            if (columns.size() > 1) {
                Element targetElement = columns.get(1);  // 두 번째 div.column 선택
                String target = targetElement.text();
                System.out.println("대상: " + target);
            } else {
                System.out.println("대상 정보가 부족하거나 없습니다.");
            }

            // 내용 크롤링
            String content = detailDoc.select("div.cont.box").text();
            System.out.println("내용: " + content);

            System.out.println("------------------------------------");
        }
        // 브라우저 닫기
        driver.quit();
    }
}
