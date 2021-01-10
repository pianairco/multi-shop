package ir.piana.business.multishop.zarinpalclient.rest;

import ir.piana.business.multishop.zarinpalclient.model.RequestModel;
import ir.piana.business.multishop.zarinpalclient.model.ResponseModel;
import ir.piana.business.multishop.zarinpalclient.model.VerifyResponseModel;
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

@RestController
public class FinanceRest {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("api/zarinpal/verify")
    public ResponseEntity response(HttpServletRequest request,
                                   @RequestParam("Authority") String authority,
                                   @RequestParam("Status") String status) {
        HttpEntity<RequestModel> verifyRequest = new HttpEntity<>(RequestModel.builder()
                .merchant_id("")
                .amount(10000)
                .description("test item")
                .callback_url("http://ns1.piana.ir/api/zarinpal/verify")
                .build());
        ResponseEntity<VerifyResponseModel> response = restTemplate.postForEntity(
                "https://api.zarinpal.com/pg/v4/payment/verify.json", verifyRequest, VerifyResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            VerifyResponseModel body = response.getBody();
            String redirect = "https://www.zarinpal.com/pg/StartPay/".concat(body.getData().getCard_pan());
            return ResponseEntity.ok(redirect);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("api/zarinpal/request")
    public String requestFromZarinpal() {
        HttpEntity<RequestModel> request = new HttpEntity<>(RequestModel.builder()
                .merchant_id("")
                .amount(10000)
                .description("test item")
                .callback_url("http://ns1.piana.ir/api/zarinpal/verify")
                .build());
        ResponseEntity<ResponseModel> response = restTemplate.postForEntity(
                "https://api.zarinpal.com/pg/v4/payment/request.json", request, ResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            ResponseModel body = response.getBody();
            String redirect = "https://www.zarinpal.com/pg/StartPay/".concat(body.getData().getAuthority());
            return "redirect:".concat(redirect);
        }
        throw new RuntimeException();
    }
}
