package com.winter.model;

import lombok.Data;

import java.util.Date;

/***
 @author shenhy
 @create 2021-04-11 15:25 
 ***/
@Data
public class RepayLoanInput {

    private String loanReceiptNo;//贷款借据号

    private String repayDate;//还款日期

    private double repayLoanAmount;//本次还款本金金额

    private double repayInterest;//本次还款利息

}
