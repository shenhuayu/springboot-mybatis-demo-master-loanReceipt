package com.winter.mapper;

import com.winter.model.LoanReceipt;

import java.util.List;
import java.util.Map;

public interface LoanReceiptMapper {
    int deleteByPrimaryKey(String loanreceiptno);

    int insert(LoanReceipt record);

    int insertSelective(LoanReceipt record);

    LoanReceipt selectByPrimaryKey(String loanreceiptno);

    LoanReceipt selectLoanSeq(Map<String,Object> map);

    List<LoanReceipt> selectLoanByIdCard(String idCard);

    int updateByPrimaryKeySelective(LoanReceipt record);

    int updateByPrimaryKey(LoanReceipt record);
}