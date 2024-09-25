package com.financial.infra.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class SamsungCardEventCrawler {
    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.samsung}")
    private String eventUrl;

    public void crawlEventLinks() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();

        driver.get(eventUrl + "UHPPBE1401M0.jsp");  // 이벤트 목록 페이지로 이동
        Map<String, String> map = new HashMap<>();

        List<WebElement> eventElements = driver.findElements(By.cssSelector("ul.item_cv_wrap_vinyl05 li"));

        for (WebElement eventElement : eventElements) {
            // li 태그의 id 값 추출
            String liId = eventElement.getAttribute("id");
            System.out.println("li 태그의 ID: " + liId);

            // 상세 URL 생성
            String fullEventUrl = eventUrl + "UHPPBE1403M0.jsp?cms_id=" + liId;
            System.out.println("크롤링한 URL: " + fullEventUrl);

            // map에 저장 (추후 추가 작업 가능)
            map.put(fullEventUrl, "");
        }

        for (String detailUrl : map.keySet()) {
            driver.get(detailUrl);  // 상세 페이지로 이동
            System.out.println(detailUrl);
            // 페이지 소스를 가져와 Jsoup으로 파싱
            String detailPageSource = driver.getPageSource();
            Document detailDoc = Jsoup.parse(detailPageSource);

            // 잠시 대기 (페이지 로드 시간)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 1. 제목 크롤링 (div.main_vis p1)
            String title = detailDoc.select("div.main_vis p.p01").text();
            System.out.println("title: " + title);

            // 2. 기간 크롤링 (div.main_vis p2)
            String dateText = detailDoc.select("div.main_vis p.p02").text();
            String[] dateParts = dateText.split("~");
            String startDate = dateParts[0].trim();
            String endDate = dateParts[1].trim();
            System.out.println("startDate: " + startDate + ", endDate: " + endDate);

            // 3. 대상 크롤링 (dl.new_dl에서 <dt>대상카드 다음 <dd>)
            String target = "";
            for (Element dlElement : detailDoc.select("dl.new_dl dt")) {
                if (dlElement.text().contains("대상카드")) {
                    target = dlElement.nextElementSibling().text();  // 다음 태그인 <dd> 값
                    break;
                }
            }
            System.out.println("target: " + target);

            // 4. 혜택 크롤링 (dl.new_dl에서 <dt>혜택 다음 <dd>)
            String content = "";
            for (Element dlElement : detailDoc.select("dl.new_dl dt")) {
                if (dlElement.text().contains("혜택")) {
                    content = dlElement.nextElementSibling().text();  // 다음 태그인 <dd> 값
                    break;
                }
            }
            System.out.println("content: " + content);

            // 크롤링한 데이터를 출력 또는 저장할 수 있도록 가공
            System.out.println("-----------------------------------");
        }

    }
}
