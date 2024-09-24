package com.financial.infra.crawler.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WooriCardEventCrawler {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.woori}")
    private String eventUrl;

    public void crawlEventLinks() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();

        driver.get(eventUrl);  // 이벤트 목록 페이지로 이동
        Map<String, String> map = new HashMap<>();

        // 모든 이벤트 리스트를 가져오기
        List<WebElement> eventElements = driver.findElements(By.cssSelector("ul.eventList li.uiAct"));

        for (int i = 0; i < eventElements.size(); i++) {
            WebElement eventElement = eventElements.get(i);

            // 이벤트 ID 추출
            String eventId = eventElement.getAttribute("data-dtl").split("\\|")[0]; // 첫 번째 값이 이벤트 ID
            System.out.println("이벤트 ID: " + eventId);

            // 이벤트 상세 페이지로 이동
            eventElement.click();

            // 잠시 대기 (페이지 로드 시간)
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 현재 상세 페이지의 HTML 소스 가져오기
            String detailPageSource = driver.getPageSource();
            Document detailDoc = Jsoup.parse(detailPageSource);

            // 1. 제목 크롤링 (div.eventView_head h1.tit)
            String title = detailDoc.select("div.eventView_head h1.tit").text();
            System.out.println("title: " + title);

            // 2. 기간 크롤링 (div.tbInfo)
            String dateText = detailDoc.select("div.eventView_head div.tblInfo span.date").text();
            String[] dateParts = dateText.split("~");
            String startDate = dateParts[0].trim();
            String endDate = dateParts[1].trim();
            System.out.println("startDate: " + startDate + ", endDate: " + endDate);

            // 3. 대상 크롤링 (div.eventView_body div#wcmsArea의 첫 번째 dl)
            String target = detailDoc.select("div.eventView_body div#wcmsArea dl").first().text();
            System.out.println("target: " + target);

            // 4. 혜택 관련 내용 크롤링 (div.eventView_body div#wcmsArea의 두 번째 dl에서 혜택 포함)
            String content = "";
            for (WebElement dlElement : driver.findElements(By.cssSelector("div.eventView_body div#wcmsArea dl"))) {
                if (dlElement.getText().contains("혜택")) {
                    content = dlElement.getText();
                    break;
                }
            }
            System.out.println("content: " + content);

            // 크롤링한 데이터를 출력하거나 저장할 수 있는 방식으로 가공
            System.out.println("-----------------------------------");

            // 뒤로가기 (목록 페이지로 이동)
            driver.navigate().back();

            // 목록 페이지가 다시 로드되면 잠시 대기
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 목록을 다시 로드하고, 다시 모든 이벤트 리스트를 가져옴 (다시 조회)
            eventElements = driver.findElements(By.cssSelector("ul.eventList li.uiAct"));
        }
        driver.quit();  // 브라우저 닫기

    }
}
