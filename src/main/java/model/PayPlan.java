package model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayPlan {
    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("amount_to_pay")
    @SerializedName("amount_to_pay")
    private double amountToPay;
    @JsonProperty("installment_frequency")
    @SerializedName("installment_frequency")
    private String installmentFrequency;
    @JsonProperty("installment_amount")
    @SerializedName("installment_amount")
    private double installmentAmount;
    @JsonProperty("start_date")
    @SerializedName("start_date")
    private String startDate;
    @JsonProperty("debt_id")
    @SerializedName("debt_id")
    private int debtId;

}
