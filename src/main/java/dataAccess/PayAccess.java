package dataAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import model.Pay;

@Component
public class PayAccess {
	
	@Value("${com.example.demo.pay.api.url}")
    private String paymentApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<Pay> getAllPayments() {
        ResponseEntity<List<Pay>> paymentResponse = restTemplate.exchange(paymentApiUrl, HttpMethod.GET, null,
                                                                        new ParameterizedTypeReference<List<Pay>>() {});
        return paymentResponse.getBody();
    }
}
