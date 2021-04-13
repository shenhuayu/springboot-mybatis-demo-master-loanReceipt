package com.winter.model;

import lombok.Data;

import java.util.Date;

/***
 @author shenhy
 @create 2021-04-09 10:46 
 ***/
@Data
public class LoanReceiptInput {
    private String loanreceiptno;//贷款借据号
    private String idcard;//身份证
    private String loandate;//借款日期
    private Double loanAmt;//借款金额
    private Double loanInterest;//借款日利率
}
