package ir.piana.business.multishop.zarinpalclient.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.zarinpalclient.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class FinanceRest {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("api/zarinpal/request")
    public String requestFromZarinpal(HttpServletResponse httpResponse) throws IOException {
        HttpEntity<RequestModel> request = new HttpEntity<>(RequestModel.builder()
                .merchant_id("71ca587b-7b1b-451d-a26d-72155e11e88f")
                .amount(10000)
                .description("test item")
                .metadata(Arrays.asList(
                        MetaDataModel.builder()
                                .mobile("09391366128")
                                .email("rahmatii1366@gmail.com")
                                .build()))
                .callback_url("http://ns1.piana.ir/api/zarinpal/verify")
                .build());
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.zarinpal.com/pg/v4/payment/request.json", request, String.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            ResponseModel responseModel = objectMapper.readValue(response.getBody(), ResponseModel.class);
            String redirect = "https://www.zarinpal.com/pg/StartPay/".concat(responseModel.getData().getAuthority());
            httpResponse.sendRedirect(redirect);
            return null;
        }
        throw new RuntimeException();
    }

    @GetMapping("api/zarinpal/verify")
    public ResponseEntity response(HttpServletRequest request, HttpServletResponse httpResponse,
                                   @RequestParam("Authority") String authority,
                                   @RequestParam("Status") String status) throws IOException {
        HttpEntity<VerifyRequestModel> verifyRequest = new HttpEntity<>(VerifyRequestModel.builder()
                .merchant_id("71ca587b-7b1b-451d-a26d-72155e11e88f")
                .amount(10000)
                .authority(authority)
                .build());
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.zarinpal.com/pg/v4/payment/verify.json", verifyRequest, String.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            VerifyResponseModel verifyResponseModel = objectMapper.readValue(response.getBody(), VerifyResponseModel.class);
            String redirect = "/api/zarinpal/success?pan=".concat(verifyResponseModel.getData().getCard_pan());
            httpResponse.sendRedirect(redirect);
            return null;
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("api/zarinpal/success")
    public String getSuccess(@RequestParam("pan") String pan) {
        return pan;
    }

    @GetMapping("api/zarinpal/redirect")
    public String redirect(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect("/api/zarinpal/success?pan=32423");
        return null;
    }
}
