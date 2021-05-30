package com.example.demo;

import model.PayPlan;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.joda.time.LocalDate;

import dataAccess.DebtAccess;
import dataAccess.PayAccess;
import dataAccess.PayPlanAccess;
import dataTransfer.DebtTransfer;
import dataTransfer.PayTime;

import model.Debt;
import model.Pay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MainService {
	Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    DebtAccess debtObj;

    @Autowired
    PayPlanAccess payPlanObj;

    @Autowired
    PayAccess payObj;

    public void displayDebts() {
        List<DebtTransfer> debts = summarizeDebts();
        debts.stream().forEach(debt-> System.out.println(debt.toString()));
    }

    private List<DebtTransfer> summarizeDebts() {
        List<DebtTransfer> summary = null;
        try {
            List<Debt> debts = getDebts();
            Map<Integer, PayPlan> debtToPaymentPlan = getDebtIdToPaymentPlan();
            Map<Integer, List<Pay>> paymentPlanToPayment = getPaymentPlanIdToPayments();

            summary = summarizeDebt(debts, debtToPaymentPlan, paymentPlanToPayment);
        }
        catch(Exception e) {
            logger.error("Unexpected exception while gathering debt and payment information" , e);
        }
        return summary;
    }

    public List<DebtTransfer> summarizeDebt(List<Debt> debts, Map<Integer, PayPlan> debtToPaymentPlans,
                                        Map<Integer, List<Pay>> paymentPlanToPayments) {

        List<DebtTransfer> summary = new ArrayList<>();

        for(Debt debt : debts) {
        	DebtTransfer debtDTO = new DebtTransfer(debt);
            if(debtToPaymentPlans.containsKey(debt.getId())) {
                PayPlan plan = debtToPaymentPlans.get(debt.getId());
                debtDTO.setInPayPlan(true);
                debtDTO.setRemainingAmount(calculateRemainingAmount(debt, plan, paymentPlanToPayments));
                debtDTO.setNextPayDueDate(getNextPaymentDueDate(plan, debtDTO.getRemainingAmount()));
            } else {
                debtDTO.setInPayPlan(false);
                debtDTO.setRemainingAmount(debtDTO.getAmount());
                debtDTO.setNextPayDueDate(null);
            }
            summary.add(debtDTO);
        }
        return summary;
    }

    public String getNextPaymentDueDate(PayPlan plan, double remainingAmount) {
        if(plan == null || remainingAmount == 0.0) {
            return null;
        }

        LocalDate startDate = LocalDate.parse(plan.getStartDate());
        LocalDate today = LocalDate.now();
        int timeDiff = startDate.getDayOfWeek() - today.getDayOfWeek();
        DateFormat formatter = new SimpleDateFormat("YYY-MM-dd");

        try {
            if(plan.getInstallmentFrequency().equals(PayTime.WEEKLY.name())) {
                if(timeDiff <= 0) {
                    timeDiff += 7;
                }
            } else if(plan.getInstallmentFrequency().equals(PayTime.BI_WEEKLY.name())) {
                if(timeDiff <= 0) {
                    timeDiff += 14;
                }
            } else {
                throw new IllegalArgumentException("Pay frequency not defined");
            }
        }
        catch(IllegalArgumentException e) {
            logger.error("Error while calculating the payment date for debt id " + plan.getDebtId());
            e.printStackTrace();
        }
        return formatter.format(today.plusDays(timeDiff).toDate());
    }

    public double calculateRemainingAmount(Debt debt, PayPlan plan, Map<Integer, List<Pay>> planIdToPayments) {
        double remainingAmount = debt.getAmount();
        List<Pay> payments = planIdToPayments.getOrDefault(plan.getId(), new ArrayList<>());
        for(Pay payment : payments) {
            remainingAmount -= payment.getAmount();
        }

        return Math.round(remainingAmount * 100.0) / 100.0;
    }

    private List<Debt> getDebts() {
        return debtObj.getAllDebts().stream().collect(Collectors.toList());
    }

    private Map<Integer, PayPlan> getDebtIdToPaymentPlan() {
        return payPlanObj.getAllPaymentPlans().stream()
                                                    .collect(Collectors.toMap(PayPlan::getDebtId, Function.identity()));
    }

    private Map<Integer, List<Pay>> getPaymentPlanIdToPayments() {
        Map<Integer, List<Pay>> planToPaymentsMap = new HashMap<>();
        List<Pay> payments = payObj.getAllPayments().stream().collect(Collectors.toList());

        for(Pay payment : payments) {
            if(planToPaymentsMap.containsKey(payment.getPaymentPlanId())) {
                planToPaymentsMap.get(payment.getPaymentPlanId()).add(payment);
            } else {
                List<Pay> newPaymentList = new ArrayList<>();
                newPaymentList.add(payment);
                planToPaymentsMap.put(payment.getPaymentPlanId(), newPaymentList);
            }
        }
        return planToPaymentsMap;
    }

}
