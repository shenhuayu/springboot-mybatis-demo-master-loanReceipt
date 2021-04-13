package com.winter.utils;

/***
 @author shenhy
 @create 2021-04-09 15:09 
 ***/
public class LittleFunctionUtils {

    public static String getNextSeq(String seq) {
        String res = "" ;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i)=='0')
                continue;
            res = res + seq.charAt(i);
        }
        //res = new StringBuffer(res).reverse().toString();
        int resN = Integer.valueOf(res) + 1;
        res = String.valueOf(resN);
        int cnt = res.length();
        for (int i = 0; i < 4 - cnt; i++) {
            res = "0" + res;
        }
        return res;
    }

    public static void main(String[] args) {
        //System.out.println(getNextSeq("0002"));
        String str = "360428199007291631_20170328_0001" ;
        System.out.println(str.split("_")[0]);
    }
}
