package dataAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import model.PayPlan;

@Component
public class PayPlanAccess {

	@Value("${com.example.demo.payplan.api.url}")
    private String paymentPlanApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<PayPlan> getAllPaymentPlans() {
        ResponseEntity<List<PayPlan>> paymentPlanResponse = restTemplate.exchange(paymentPlanApiUrl, HttpMethod.GET, null,
                                                                            new ParameterizedTypeReference<List<PayPlan>>() {});

        return paymentPlanResponse.getBody();

    }
}
