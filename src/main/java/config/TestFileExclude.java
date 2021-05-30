package config;

import com.example.demo.MainService;
import dataAccess.DebtAccess;
import dataAccess.PayAccess;
import dataAccess.PayPlanAccess;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
@EnableAutoConfiguration
public class TestFileExclude {

    @Bean
    public MainService getDebtService() {
        return new MainService();
    }

    @Bean
    public DebtAccess getDebtDao() {
        return new DebtAccess();
    }

    @Bean
    public PayAccess getPaymentDao() {
        return new PayAccess();
    }

    @Bean
    public PayPlanAccess getPaymentPlanDao() {
        return new PayPlanAccess();
    }


}
