package dataTransfer;

import model.Pay;
import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
public class PayTransfer {
    private int paymentPlanId;
    private double amount;
    private String date;

    public PayTransfer(Pay payment) {
        this.paymentPlanId = payment.getPaymentPlanId();
        this.amount = payment.getAmount();
        this.date = payment.getDate();
    }
}