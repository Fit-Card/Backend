package com.financial.infra.crawler.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KbCardEventCrawler {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.kb}")
    private String eventUrl;

    public void crawlEventLinks() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();

        // 첫 번째 페이지로 이동
        driver.get(eventUrl);

        Map<String, Integer> map = new HashMap<>();
        // 각 페이지의 데이터를 크롤링
        for (int i = 1; i <= 10; i++) {
            // 페이지 소스를 가져와 Jsoup으로 파싱
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            // Jsoup을 사용해 크롤링
            doc.select(".eventList li > a").forEach(element -> {
                String eventLink = element.attr("href");
                System.out.println("크롤링한 링크: " + eventLink);
                map.put(eventLink, 0);
            });

            // 다음 페이지로 이동
            if (i < 10) {
                driver.findElement(By.xpath("//a[text()='" + (i + 1) + "']")).click();
                try {
                    Thread.sleep(2000);  // 페이지 로드 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 브라우저 닫기
        System.out.println(map.size());
        driver.quit();
    }
}
