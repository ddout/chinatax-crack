package com.cdhy.ei.service;

import java.util.Map;

public interface IChinataxCrackService {
    /**
     * 查验发票数据
     * 
     * @param parm
     * @return
     */
    Map<String, Object> check(Map<String, Object> parm);

    /**
     * 获取验证码
     * 
     * @param parm
     * @return
     */
    Map<String, Object> checkBy(Map<String, Object> parm);

}
