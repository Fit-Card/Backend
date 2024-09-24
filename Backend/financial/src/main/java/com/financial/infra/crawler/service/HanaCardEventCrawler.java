package com.financial.infra.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HanaCardEventCrawler {
    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${event-url.hana}")
    private String eventUrl;

    public void crawlEventLinks() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();

        // 첫 번째 페이지로 이동
        driver.get(eventUrl);

        Map<String, String> map = new HashMap<>();
        int currentPage = 0;  // 시작 페이지 번호 설정

        while (true) {
            // 페이지 소스를 가져와 Jsoup으로 파싱
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            // 이벤트 크롤링 (이전 코드와 동일)
            Pattern pattern = Pattern.compile("goView\\('([0-9]+)','[0-9]+'\\)");
            doc.select("ul.thumb_ty li.list a").forEach(element -> {
                String eventLink = element.attr("href");
                Matcher matcher = pattern.matcher(eventLink);
                if (matcher.find()) {
                    String evnSeq = matcher.group(1);
                    String fullEventUrl = "https://www.hanacard.co.kr/OPP35000001D.web?schID=ncd&mID=OPP35000001D&EVN_SEQ=" + evnSeq;
                    System.out.println("크롤링한 URL: " + fullEventUrl);
                    map.put(fullEventUrl, "");
                }
            });

            // 현재 페이지 번호 가져오기 (li.select의 텍스트 값에서 숫자 추출)
            WebElement selectedPageElement = driver.findElement(By.cssSelector("div.paginate ul li.select"));
            int selectedPageNumber = Integer.parseInt(selectedPageElement.getText().trim());

            // 다음 페이지 번호가 select된 페이지 이후인지 확인
            if (selectedPageNumber == currentPage) {
                System.out.println("마지막 페이지입니다. 크롤링을 종료합니다.");
                break;  // 마지막 페이지에 도달한 경우 루프 종료
            }

            // 페이지가 더 있는 경우 next 버튼을 클릭하여 다음 페이지로 이동
            try {
                WebElement nextButton = driver.findElement(By.cssSelector("div.paginate div.next a"));
                nextButton.click();
                Thread.sleep(2000);  // 페이지가 로드될 시간을 대기
                currentPage = selectedPageNumber;  // 현재 페이지 번호 갱신
            } catch (Exception e) {
                System.out.println("다음 페이지로 이동할 수 없습니다.");
                break;  // 더 이상 페이지가 없으면 종료
            }
        }

        for (String url : map.keySet()) {
            // URL로 이동하여 상세 정보 크롤링
            driver.get(url);
            String detailPageSource = driver.getPageSource();
            Document detailDoc = Jsoup.parse(detailPageSource);

            // 1. title 크롤링 (div class="event_title" > h5)
            String title = detailDoc.select("div.event_title h5").text();
            System.out.println("title: " + title);

            // 2. 기간 크롤링 (ul 내부의 '기간'이라는 문자열이 포함된 li)
            String startDate = "";
            String endDate = "";
            for (var liElement : detailDoc.select("div.event_title ul li")) {
                if (liElement.text().contains("기간")) {
                    String[] dateParts = liElement.text().replace("기간 ", "").split(" ~ ");
                    startDate = dateParts[0].trim();
                    endDate = dateParts[1].trim();
                    System.out.println("startDate: " + startDate + ", endDate: " + endDate);
                }

                if (liElement.text().contains("대상")) {
                    String target = liElement.text().replace("대상 ", "").trim();
                    System.out.println("target: " + target);
                }
            }


            // 4. 내용 크롤링 (div class="event_view")
            String content = detailDoc.select("div.event_view").text();
            System.out.println("content: " + content);

            System.out.println("--------------------------------");
        }
        System.out.println(map.size());
        driver.quit();
    }
}
