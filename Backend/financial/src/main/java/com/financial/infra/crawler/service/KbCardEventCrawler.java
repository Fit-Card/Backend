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

        Map<String, String[]> map = new HashMap<>();

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

                    // map에 URL을 key로, title, startDate, endDate를 이어서 배열로 value로 저장
                    String[] eventData = {title, startDate, endDate};
                    map.put(fullEventUrl, eventData);  // Map에 추가
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

        // 상세 페이지 크롤링
        for (String detailUrl : map.keySet()) {
            driver.get(detailUrl);  // 상세 페이지로 이동

            // map에서 title, startDate, endDate를 가져오기
            String[] eventData = map.get(detailUrl);
            String title = eventData[0];
            String startDate = eventData[1];
            String endDate = eventData[2];

            // 상세 페이지 크롤링 진행
            System.out.println("크롤링 중 URL: " + detailUrl);
            System.out.println("title: " + title);
            System.out.println("startDate: " + startDate + ", endDate: " + endDate);

            // 페이지 소스를 가져와 Jsoup으로 파싱
            String detailPageSource = driver.getPageSource();
            Document detailDoc = Jsoup.parse(detailPageSource);

            // 대상 크롤링
            Elements columns = detailDoc.select("div.column p");
            if (columns.size() > 1) {
                Element targetElement = columns.get(1);  // 두 번째 div.column 선택
                String target = targetElement.text();
                System.out.println("대상: " + target);
            } else {
                String target = "자세한 정보는 이벤트 링크에 접속하여 확인하세요.";
            }

            // 내용 크롤링 (div.cont.box 안의 모든 p 태그 크롤링)
            Elements contentElements = detailDoc.select("div.cont.box p");
            StringBuilder contentBuilder = new StringBuilder();
            for (Element contentElement : contentElements) {
                contentBuilder.append(contentElement.text()).append("\n");  // 각 p 태그의 텍스트를 content에 추가
            }
            String content = contentBuilder.toString().trim();// 공백 제거 후 저장
            if(content.equals("")) content = "자세한 정보는 이벤트 링크에 접속하여 확인하세요.";
            System.out.println("내용: " + content);

            System.out.println("------------------------------------");
        }

        // 브라우저 닫기
        driver.quit();
    }
}
