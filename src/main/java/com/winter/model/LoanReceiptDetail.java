package com.winter.model;

import lombok.Data;

import java.util.Date;

@Data
public class LoanReceiptDetail {

    private String loanreceiptno;//贷款借据号

    private String idcard;//身份证

    private Date loandate;//借款日期

    private Date repaydate;//还款日期

    private Integer interestdays;//计息天数

    private Integer repaymentperiod;//还款期数

    private Double currentremainprincipal;//本期剩余本金

    private Double currentrepayprincipal;//本期尝还本金

    private Double currentinterestpayment;//本期应还利息

    private Double currentinterestpayed;//本期已还利息

    private Double currentinterestnotpay;//本期未还利息

}