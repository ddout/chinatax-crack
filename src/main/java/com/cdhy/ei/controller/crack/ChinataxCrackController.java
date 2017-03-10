package com.cdhy.ei.controller.crack;

import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdhy.commons.utils.exception.BizException;
import com.cdhy.commons.utils.model.Result;
import com.cdhy.ei.service.IChinataxCrackService;

@Controller
@RequestMapping("/taxCrackController")
public class ChinataxCrackController {
    private static final Logger log = Logger.getLogger(ChinataxCrackController.class);

    @Autowired
    private IChinataxCrackService service;

    @RequestMapping("/check")
    @ResponseBody
    public Object check(String callback, @RequestParam Map<String, Object> parm) {
	Result obj = new Result();
	try {
	    Map<String, Object> result = service.check(parm);
	    obj.setRows(result);
	} catch (BizException e) {
	    log.debug("操作失败", e);
	    obj.setResult(Result.RESULT_ERROR);
	    obj.setMsg(e.getMessage());
	} catch (Exception e) {
	    log.error("操作失败", e);
	    obj.setResult(Result.RESULT_ERROR);
	    obj.setMsg(Result.RESULT_ERROR_MSG);
	}
	if (null != parm.get("callback") && !"".equals(parm.get("callback").toString().trim())) {
	    return new JSONPObject(parm.get("callback").toString().trim(), obj);
	} else {
	    return obj;
	}
    }
    
    @RequestMapping("/checkBy")
    @ResponseBody
    public Object checkBy(String callback, @RequestParam Map<String, Object> parm) {
	Result obj = new Result();
	try {
	    Map<String, Object> result = service.checkBy(parm);
	    obj.setRows(result);
	} catch (BizException e) {
	    log.debug("操作失败", e);
	    obj.setResult(Result.RESULT_ERROR);
	    obj.setMsg(e.getMessage());
	} catch (Exception e) {
	    log.error("操作失败", e);
	    obj.setResult(Result.RESULT_ERROR);
	    obj.setMsg(Result.RESULT_ERROR_MSG);
	}
	if (null != parm.get("callback") && !"".equals(parm.get("callback").toString().trim())) {
	    return new JSONPObject(parm.get("callback").toString().trim(), obj);
	} else {
	    return obj;
	}
    }
}
