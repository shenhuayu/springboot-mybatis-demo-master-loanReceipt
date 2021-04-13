package com.winter.Controller;

import com.winter.model.LoanReceipt;
import com.winter.model.LoanReceiptDetail;
import com.winter.model.LoanReceiptInput;
import com.winter.model.RepayLoanInput;
import com.winter.model.User;
import com.winter.service.user.UserService;
import com.winter.utils.DateUtils;
import com.winter.utils.LittleFunctionUtils;
import com.winter.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2017/8/16
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/inputIndex")
    public String inputIndex(){
        logger.info("run inputIndex here......");
        return "input";
    }

    @RequestMapping("/input")
    @ResponseBody
    public String input(String idCard,String loanAmt){
        logger.info("idCard="+idCard+",loanAmt="+loanAmt);
        return "{\"idCard\":"+idCard+",\"loanAmt\":"+loanAmt+"}";
    }

    //单笔贷款借据输入
    @RequestMapping("/loanReceiptInput")
    public ModelAndView loanReceiptInput(ModelAndView modelAndView) {
        logger.info("run loanReceiptInput here......");
        LoanReceiptInput loanReceiptInput = new LoanReceiptInput();
        modelAndView.addObject("loanReceiptInput",loanReceiptInput);
        modelAndView.setViewName("loanReceiptInput");
        return modelAndView;
    }

    @RequestMapping(value="/loanReceiptRegister",method = RequestMethod.POST)
    public ModelAndView loanReceiptRegister(ModelAndView modelAndView, @ModelAttribute @Valid LoanReceiptInput loanReceiptInput , BindingResult bindingResult) {
        logger.info("run loanReceiptRegister here......loanReceiptInput="+loanReceiptInput);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        String todayStr = sdf.format(today);
        Date loanDate = null;
        try {
            loanDate = sdf.parse(loanReceiptInput.getLoandate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null==loanDate) {
            try {
                loanDate = sdf.parse(todayStr);
                loanReceiptInput.setLoandate(todayStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("idcard",loanReceiptInput.getIdcard());
        map.put("loandate",loanReceiptInput.getLoandate());
        LoanReceipt loanReceiptLast = userService.selectLoanSeq(map);
        LoanReceipt loanReceipt = new LoanReceipt();
        String nextSeq = "0001" ;
        if (null==loanReceiptLast)
            loanReceipt.setLoanreceiptno(loanReceiptInput.getIdcard()+"_"+sdf.format(loanDate)+"_0001");//固定四位数
        else {
            nextSeq = LittleFunctionUtils.getNextSeq(loanReceiptLast.getLoanseq());
            loanReceipt.setLoanreceiptno(loanReceiptInput.getIdcard()+"_"+sdf.format(loanDate)+"_"+ nextSeq);//固定四位数
        }
        loanReceipt.setLoanseq(nextSeq);
        loanReceipt.setIdcard(loanReceiptInput.getIdcard());
        loanReceipt.setLoandate(loanDate);
        loanReceipt.setDailyinterestrate(loanReceiptInput.getLoanInterest());
        loanReceipt.setSumremainprincipal(loanReceiptInput.getLoanAmt());
        loanReceipt.setLoansettlesign(false);
        userService.insertLoanReceipt(loanReceipt);
        loanReceiptInput.setLoanreceiptno(loanReceipt.getLoanreceiptno());
        modelAndView.addObject("loanReceiptInput",loanReceiptInput);
        modelAndView.setViewName("loanReceiptSuccess");
        return modelAndView;
    }

    //单笔还款输入
    @RequestMapping("/repayLoanInput")
    public ModelAndView repayLoanInput(ModelAndView modelAndView) {
        logger.info("run repayLoanInput here......");
        RepayLoanInput repayLoanInput = new RepayLoanInput();
        modelAndView.addObject("repayLoanInput",repayLoanInput);
        modelAndView.setViewName("repayLoanInput");
        return modelAndView;
    }

    @RequestMapping(value="/repayLoanRegister",method = RequestMethod.POST)
    public ModelAndView repayLoanRegister(ModelAndView modelAndView, @ModelAttribute @Valid RepayLoanInput repayLoanInput , BindingResult bindingResult) {
        logger.info("run repayLoanRegister here......repayLoanInput=" + repayLoanInput);
        //查询最近一次还款记录
        LoanReceiptDetail loanReceiptDetailLast = userService.selectByPrimaryKeyLoanReceiptDetail(repayLoanInput.getLoanReceiptNo());
        //查询该借据号对应的贷款
        LoanReceipt loanReceipt = userService.selectByPrimaryKey(repayLoanInput.getLoanReceiptNo());
        LoanReceiptDetail loanReceiptDetail = new LoanReceiptDetail();
        //处理还款记录
        if(null==loanReceiptDetailLast)
            loanReceiptDetail.setLoanreceiptno(repayLoanInput.getLoanReceiptNo()+"_1");//贷款借据号
        else
            loanReceiptDetail.setLoanreceiptno(repayLoanInput.getLoanReceiptNo()+"_"+String.valueOf(1+loanReceiptDetailLast.getRepaymentperiod()));//贷款借据号
        loanReceiptDetail.setIdcard(repayLoanInput.getLoanReceiptNo().split("_")[0]);//身份证
        //这里需要更新一下借款日期
        if (null==loanReceiptDetailLast)
            loanReceiptDetail.setLoandate(loanReceipt.getLoandate());//借款日期
        else
            loanReceiptDetail.setLoandate(loanReceiptDetailLast.getRepaydate());//借款日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date repayLoanDate = null;
        try {
            repayLoanDate = sdf.parse(repayLoanInput.getRepayDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        loanReceiptDetail.setRepaydate(repayLoanDate);//还款日期
        if (null==loanReceiptDetailLast) {//第一次还款
            //计息天数
            loanReceiptDetail.setInterestdays(DateUtils.daysBetween(loanReceiptDetail.getLoandate(),repayLoanDate)-30);
            loanReceiptDetail.setRepaymentperiod(1);//还款期数
        } else {
            //计息天数
            loanReceiptDetail.setInterestdays(DateUtils.daysBetween(loanReceiptDetail.getLoandate(),repayLoanDate));
            loanReceiptDetail.setRepaymentperiod(loanReceiptDetailLast.getRepaymentperiod()+1);//还款期数
        }
        loanReceiptDetail.setCurrentremainprincipal(loanReceipt.getSumremainprincipal());//本期剩余本金
        loanReceiptDetail.setCurrentrepayprincipal(repayLoanInput.getRepayLoanAmount());//本期尝还本金
        //本期应还利息
        loanReceiptDetail.setCurrentinterestpayment(loanReceipt.getSumremainprincipal()*loanReceiptDetail.getInterestdays()*loanReceipt.getDailyinterestrate());
        loanReceiptDetail.setCurrentinterestpayed(repayLoanInput.getRepayInterest());//本期已还利息
        double interestnotpay = loanReceipt.getSuminterestnotpay()==null?0:loanReceipt.getSuminterestnotpay();
        loanReceiptDetail.setCurrentinterestnotpay(loanReceiptDetail.getCurrentinterestpayment()+interestnotpay-loanReceiptDetail.getCurrentinterestpayed());//本期未还利息
        //保存loanReceiptDetail
        userService.insertLoanReceiptDetail(loanReceiptDetail);
        //更新loanReceipt
        //剩余本金总额
        double sumremainprincipal = loanReceipt.getSumremainprincipal()==null?0:loanReceipt.getSumremainprincipal();
        loanReceipt.setSumremainprincipal(sumremainprincipal-repayLoanInput.getRepayLoanAmount());
        double sumrepayprincipal = loanReceipt.getSumrepayprincipal()==null?0:loanReceipt.getSumrepayprincipal();
        loanReceipt.setSumrepayprincipal(sumrepayprincipal+repayLoanInput.getRepayLoanAmount());//尝还本金总额
        //应还利息总额
        double suminterestpayment = loanReceipt.getSuminterestpayment()==null?0:loanReceipt.getSuminterestpayment();
        loanReceipt.setSuminterestpayment(suminterestpayment+loanReceiptDetail.getCurrentinterestpayment());
        //已还利息总额
        double suminterestpayed = loanReceipt.getSuminterestpayed()==null?0:loanReceipt.getSuminterestpayed() ;
        loanReceipt.setSuminterestpayed(suminterestpayed+repayLoanInput.getRepayInterest());
        loanReceipt.setSuminterestnotpay(loanReceiptDetail.getCurrentinterestnotpay());//未还利息总额
        //判断该笔借据是否已经结清
        if (loanReceipt.getSuminterestnotpay()==0&&loanReceipt.getSumremainprincipal()==0) {
            loanReceipt.setCleardate(repayLoanDate);
            loanReceipt.setLoansettlesign(true);
        }
        userService.updateByPrimaryKeyLoanReceipt(loanReceipt);
        modelAndView.addObject("loanReceipt",loanReceipt);
        modelAndView.setViewName("repayLoanSuccess");
        return modelAndView;
    }

    //根据借据号-还款明细记录查询
    @RequestMapping("/repayLoanDetailInput")
    public ModelAndView repayLoanDetailInput(ModelAndView modelAndView) {
        logger.info("run repayLoanDetailInput here......");
        RepayLoanInput repayLoanInput = new RepayLoanInput();
        modelAndView.addObject("repayLoanInput",repayLoanInput);
        modelAndView.setViewName("loanReceiptDetailInput");
        return modelAndView;
    }

    @RequestMapping(value="/loanReceiptDetailShow",method = RequestMethod.POST)
    public ModelAndView repayLoanDetailSuccess(ModelAndView modelAndView, @ModelAttribute @Valid RepayLoanInput repayLoanInput , BindingResult bindingResult) {
        logger.info("run loanReceiptDetailShow here......repayLoanInput=" + repayLoanInput);
        List<LoanReceiptDetail> loanReceiptDetailList = userService.selectByPrimaryKeyLoanReceiptDetail2(repayLoanInput.getLoanReceiptNo());
        modelAndView.addObject("loanReceiptDetailList",loanReceiptDetailList);
        modelAndView.setViewName("loanReceiptDetailShow");
        return modelAndView;
    }

    //根据身份证查询名下所有借据号
    @RequestMapping("/idCardLoanReceiptInput")
    public ModelAndView idCardLoanReceiptInput(ModelAndView modelAndView) {
        logger.info("run idCardLoanReceiptInput here......");
        LoanReceiptInput loanReceiptInput = new LoanReceiptInput();
        modelAndView.addObject("loanReceiptInput",loanReceiptInput);
        modelAndView.setViewName("idCardLoanReceiptInput");
        return modelAndView;
    }

    @RequestMapping(value="/idCardLoanReceiptShow",method = RequestMethod.POST)
    public ModelAndView idCardLoanReceiptSuccess(ModelAndView modelAndView, @ModelAttribute @Valid LoanReceiptInput loanReceiptInput , BindingResult bindingResult) {
        logger.info("run loanReceiptDetailShow here......loanReceiptInput=" + loanReceiptInput);
        List<LoanReceipt> loanReceiptList = userService.selectLoanByIdCard(loanReceiptInput.getIdcard());
        modelAndView.addObject("loanReceiptList",loanReceiptList);
        modelAndView.setViewName("idCardLoanReceiptShow");
        return modelAndView;
    }

    //@ResponseBody
    //@RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    @RequestMapping("/add")
    public String addUser(User user){
        logger.info("user="+user);
        int count = userService.addUser(user);
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    public static double vertStoD(String value) {
        return StringUtils.isEmpty(value)?0:Double.valueOf(value);
    }
}
