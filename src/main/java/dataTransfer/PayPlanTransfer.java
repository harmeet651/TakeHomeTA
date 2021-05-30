package dataTransfer;

import java.text.ParseException;
import lombok.*;
import model.PayPlan;

@Data
@Getter @Setter
public class PayPlanTransfer {
    private int id;
    private double amountToPay;
    private PayTime payTime;
    private double installmentAmount;
    private String startDate;
    private int debtId;

    public PayPlanTransfer(PayPlan paymentPlan) throws ParseException {
        this.id = paymentPlan.getId();
        this.amountToPay  = paymentPlan.getAmountToPay();
        this.startDate = paymentPlan.getStartDate();
        this.debtId = paymentPlan.getDebtId();
        
        if(paymentPlan.getInstallmentFrequency().equals(PayTime.WEEKLY)) {
            this.payTime = PayTime.WEEKLY;
        } 
        else if(paymentPlan.getInstallmentFrequency().equals(PayTime.BI_WEEKLY)) {
            this.payTime = PayTime.BI_WEEKLY;
        } 
        else {
            this.payTime = PayTime.NA;
        }
    }
}
