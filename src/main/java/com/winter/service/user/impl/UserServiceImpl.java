package com.winter.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.winter.mapper.LoanReceiptDetailMapper;
import com.winter.mapper.LoanReceiptMapper;
import com.winter.mapper.UserMapper;
import com.winter.model.LoanReceipt;
import com.winter.model.LoanReceiptDetail;
import com.winter.model.User;
import com.winter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    //@Autowired
    @Resource
    private UserMapper userMapper;//这里会报错，但是并不会影响
    @Resource
    private LoanReceiptMapper loanReceiptMapper;
    @Resource
    private LoanReceiptDetailMapper loanReceiptDetailMapper;

    @Override
    public int addUser(User user) {

        return userMapper.insertSelective(user);
    }

    @Override
    public int insertLoanReceipt(LoanReceipt record) {
        return loanReceiptMapper.insert(record);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }

    @Override
    public LoanReceipt selectLoanSeq(Map<String, Object> map) {
        return loanReceiptMapper.selectLoanSeq(map);
    }

    @Override
    public LoanReceipt selectByPrimaryKey(String loanreceiptno) {
        return loanReceiptMapper.selectByPrimaryKey(loanreceiptno);
    }

    @Override
    public List<LoanReceipt> selectLoanByIdCard(String idCard) {
        return loanReceiptMapper.selectLoanByIdCard(idCard);
    }

    @Override
    public LoanReceiptDetail selectByPrimaryKeyLoanReceiptDetail(String loanreceiptno) {
        return loanReceiptDetailMapper.selectByPrimaryKeyLoanReceiptDetail(loanreceiptno);
    }

    @Override
    public List<LoanReceiptDetail> selectByPrimaryKeyLoanReceiptDetail2(String loanreceiptno) {
        return loanReceiptDetailMapper.selectByPrimaryKeyLoanReceiptDetail2(loanreceiptno);
    }

    @Override
    public int insertLoanReceiptDetail(LoanReceiptDetail record) {
        return loanReceiptDetailMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeyLoanReceipt(LoanReceipt record) {
        return loanReceiptMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeyLoanReceiptDetail(LoanReceiptDetail record) {
        return loanReceiptDetailMapper.updateByPrimaryKey(record);
    }
}
