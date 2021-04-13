package com.winter.mapper;

import com.winter.model.LoanReceiptDetail;

import java.util.List;

public interface LoanReceiptDetailMapper {
    int deleteByPrimaryKey(String loanreceiptno);

    int insert(LoanReceiptDetail record);

    int insertSelective(LoanReceiptDetail record);

    LoanReceiptDetail selectByPrimaryKeyLoanReceiptDetail(String loanreceiptno);

    List<LoanReceiptDetail> selectByPrimaryKeyLoanReceiptDetail2(String loanreceiptno);

    LoanReceiptDetail selectByPrimaryKey(String loanreceiptno);

    int updateByPrimaryKeySelective(LoanReceiptDetail record);

    int updateByPrimaryKey(LoanReceiptDetail record);
}