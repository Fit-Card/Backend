package com.fitcard.infra;

import com.fitcard.domain.card.benefit.model.CardBenefit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CardGorillaWebClientService {
    private final String apiUrl = "https://api.card-gorilla.com:8080/v1/cards/";
//    private final CardBenefit cardBenefit;

    public void fetchCardBenefit(int cardId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + cardId;
        try {
            // API로부터 JSON 형식의 응답을 받음
            String response = restTemplate.getForObject(url, String.class);

            // 응답이 JSON 형식일 경우, JSONObject로 변환 후 데이터 처리
            JSONObject cardData = new JSONObject(response);

            JSONArray benefits = cardData.getJSONArray("key_benefit");

            System.out.println(benefits);

            for (int i = 0; i < benefits.length(); i++) {
                JSONObject benefitData = benefits.getJSONObject(i);
                String benefitType = benefitData.getJSONObject("cate").getString("name");
                String comment = benefitData.getString("comment");
                String info = benefitData.getString("info");

                System.out.println(benefitType);
                System.out.println(comment);
                System.out.println(info);

//                Integer amountLimit = extractAmountLimit(info);
//                String countLimit = extractCountLimit(info);
//                Integer minPayment = extractMinPayment(info);
//                Double benefitValue = extractBenefitValue(info);
//                Integer benefitPer = extractBenefitPer(info);
            }

        } catch (HttpClientErrorException.NotFound e) {
            // 404 에러를 무시하고 다음 카드로 넘어가도록 처리
            System.out.println("카드 ID " + cardId + "에 대한 데이터가 존재하지 않습니다. 다음 카드로 넘어갑니다.");
        } catch (Exception e) {
            System.out.println("카드 ID " + cardId + "의 데이터를 가져오는 데 실패했습니다.");
            e.printStackTrace();
        }
    }
}