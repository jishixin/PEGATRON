package com.jishixin.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/*
  User: 晨梦意志
  Date: 2019/6/12
  Time: 14:04
  Notes:
*/
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeActual.toUpperCase().equals(verifyCodeExpected)){
            return false;
        }
        return true;
    }
}
