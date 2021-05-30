package dataTransfer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import lombok.*;
import model.Debt;

@Data
@Getter
@Setter
public class DebtTransfer {
    private int id;
    private double amount;
    @SerializedName("is_in_pay_plan")
    private boolean isInPayPlan;
    @SerializedName("remaining_amount")
    private double remainingAmount;
    @SerializedName("next_pay_due_date")
    private String nextPayDueDate;

    public DebtTransfer(Debt debt) {
        this.id = debt.getId();
        this.amount = debt.getAmount();
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(this);
    }
}
