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

        Map<String, String> map = new HashMap<>();

        // 정규식을 사용하여 eventNum을 추출하는 패턴
        String pattern = "goDetail\\('([0-9]+)'";
        Pattern regex = Pattern.compile(pattern);

        for (int i = 1; i <= 10; i++) {
            // 페이지 소스를 가져와 Jsoup으로 파싱
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            // Jsoup을 사용해 크롤링
            doc.select(".eventList li > a").forEach(element -> {
                String eventLink = element.attr("href");

                // 정규식으로 이벤트 번호 추출
                Matcher matcher = regex.matcher(eventLink);
                if (matcher.find()) {
                    String eventNum = matcher.group(1);

                    // 이벤트 번호를 포함한 전체 URL 생성
                    String fullEventUrl = eventUrl + "?mainCC=a&eventNum=" + eventNum;
                    String title = element.select("span.subject").text();
                    String date = element.select("span.date").text();

                    String[] dateParts = date.split(" ~ ");
                    String startDate = dateParts[0];
                    String endDate = dateParts[1];
                    // map에 URL을 key로, title, startDate, endDate를 이어서 value로 저장
                    String value = "title=" + title + ", startDate=" + startDate + ", endDate=" + endDate;
                    System.out.println("저장된 값: " + value);
                    map.put(fullEventUrl, value);  // Map에 추가
                }
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
