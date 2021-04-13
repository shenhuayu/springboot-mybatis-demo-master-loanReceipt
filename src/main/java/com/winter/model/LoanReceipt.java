package com.winter.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoanReceipt {

    private String loanreceiptno;//贷款借据号

    private String loanseq;//贷款序号

    private String idcard;//身份证

    private Date loandate;//借款日期

    private Date cleardate;//结清日期

    private Double dailyinterestrate;//贷款日利率

    private Double sumremainprincipal;//剩余本金总额

    private Double sumrepayprincipal;//尝还本金总额

    private Double suminterestpayment;//应还利息总额

    private Double suminterestpayed;//已还利息总额

    private Double suminterestnotpay;//未还利息总额

    private Boolean loansettlesign;//贷款结清标志

}