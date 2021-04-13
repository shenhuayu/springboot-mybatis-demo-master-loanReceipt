package com.winter.service.user;

import com.winter.model.LoanReceipt;
import com.winter.model.LoanReceiptDetail;
import com.winter.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    int addUser(User user);

    int insertLoanReceipt(LoanReceipt record);

    List<User> findAllUser(int pageNum, int pageSize);

    LoanReceipt selectLoanSeq(Map<String,Object> map);

    LoanReceipt selectByPrimaryKey(String loanreceiptno);

    List<LoanReceipt> selectLoanByIdCard(String idCard);

    int updateByPrimaryKeyLoanReceipt(LoanReceipt record);

    int updateByPrimaryKeyLoanReceiptDetail(LoanReceiptDetail record);

    LoanReceiptDetail selectByPrimaryKeyLoanReceiptDetail(String loanreceiptno);
    List<LoanReceiptDetail> selectByPrimaryKeyLoanReceiptDetail2(String loanreceiptno);

    int insertLoanReceiptDetail(LoanReceiptDetail record);

}
