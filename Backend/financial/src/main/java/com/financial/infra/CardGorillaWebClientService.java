package com.financial.infra;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.card.repository.BankCardRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CardGorillaWebClientService {
    private final String apiUrl = "https://api.card-gorilla.com:8080/v1/cards/";
    private final BankCardRepository bankCardRepository;

    public CardGorillaWebClientService(BankCardRepository bankCardRepository) {
        this.bankCardRepository = bankCardRepository;
    }

    public void fetchCardInfo(int cardId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + cardId;
        try {
            // API로부터 JSON 형식의 응답을 받음
            String response = restTemplate.getForObject(url, String.class);

            // 응답이 JSON 형식일 경우, JSONObject로 변환 후 데이터 처리
            JSONObject cardData = new JSONObject(response);

            // 카드 정보 출력 (예시로 카드 이름을 출력)
            String cardName = cardData.getString("name");
            JSONObject cardImg = cardData.getJSONObject("card_img");
            JSONObject cardCorp = cardData.getJSONObject("corp");
            String cardCorpName = cardCorp.getString("name");
            String cardImageUrl = cardImg.getString("url");
            String cardType = cardData.getString("cate");
//            System.out.println("카드 이름: " + cardName);
//            System.out.println("카드 이미지: " + cardImageUrl);

            Long annualFee = 0L;
            Long abroadAnnualFee = 0L;

            if (cardData.has("annual_fee_basic")) {
                String annualFeeBasic = cardData.getString("annual_fee_basic");

                // 국내전용과 해외겸용 연회비를 추출
                annualFee = parseFee(annualFeeBasic, "국내전용");
                abroadAnnualFee = parseFee(annualFeeBasic, "해외겸용");

                // 연회비 출력
//                System.out.println("국내전용 연회비: " + annualFee);
//                System.out.println("해외겸용 연회비: " + abroadAnnualFee);
            }

            int createCheckType = (cardType.equals("CHK")) ? 1 : 0;
            int bcCheck = cardName.contains("(비씨)") ? 1 : 0;

//            System.out.println("createCheckType: " + createCheckType);
//            System.out.println("BC 여부: " + bcCheck);

            BankCard bankCard = new BankCard(
                    String.valueOf(cardId),
                    cardName,                // 카드 이름
                    annualFee.intValue(),     // 연회비
                    abroadAnnualFee.intValue(), // 해외 연회비
                    createCheckType == 1,     // 체크카드 여부
                    bcCheck == 1,             // BC 여부
                    cardImageUrl              // 카드 이미지 URL
            );

            bankCardRepository.save(bankCard);

        } catch (HttpClientErrorException.NotFound e) {
            // 404 에러를 무시하고 다음 카드로 넘어가도록 처리
            System.out.println("카드 ID " + cardId + "에 대한 데이터가 존재하지 않습니다. 다음 카드로 넘어갑니다.");
        } catch (Exception e) {
            System.out.println("카드 ID " + cardId + "의 데이터를 가져오는 데 실패했습니다.");
            e.printStackTrace();
        }
    }

    private Long parseFee(String feeText, String feeType) {
        String[] fees = feeText.split(" / ");
        for (String fee : fees) {
            if (fee.contains(feeType)) {
                String numericFee = fee.replaceAll("[^0-9]", "");  // 숫자가 아닌 문자를 제거
                return numericFee.isEmpty() ? 0L : Long.parseLong(numericFee);  // Long 타입으로 변환
            }
        }
        return 0L;  // '없음'으로 처리된 경우 0을 반환
    }
}