package dataAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import model.Debt;

@Component
public class DebtAccess {
	
	@Value("${com.example.demo.debt.api.url}")
    private String debtApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<Debt> getAllDebts() {
        ResponseEntity<List<Debt>> debtResponse = restTemplate.exchange(debtApiUrl, HttpMethod.GET, null,
                                                                        new ParameterizedTypeReference<List<Debt>>() {});
        return debtResponse.getBody();
    }
}
