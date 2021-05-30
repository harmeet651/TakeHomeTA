package model;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Pay {
	
    @JsonProperty("payment_plan_id")
    @SerializedName("payment_plan_id")
    private int paymentPlanId;
    @JsonProperty("amount")
    @SerializedName("amount")
    private double amount;
    @JsonProperty("date")
    @SerializedName("date")
    private String date;

}
