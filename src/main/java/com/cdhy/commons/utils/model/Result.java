package com.cdhy.commons.utils.model;

import com.cdhy.commons.utils.exception.BizException;

public class Result implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6147619418043554896L;
    /** 返回结果； */
    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_ERROR = "ERROR";
    public static final String RESULT_ERROR_MSG = "系统错误";

    /** 返回结果说明 */
    private String msg = "操作成功";
    /** 返回结果；默认为success,如果处理异常或失败，需要返回error */
    private String result = RESULT_SUCCESS;
    /** 返回的数据 */
    private Object rows;
    /** 总条数 */
    private int total;
    public static Result gerErrorResult(String msg) {
	Result rs = new Result();
	rs.setResult(RESULT_ERROR);
	rs.setMsg(msg);
	return rs;
    }

    public Result() {
	super();
    }

    public Result(String msg, String result, Object data) {
	super();
	this.msg = msg;
	this.result = result;
	this.rows = data;
    }

    public Result(String msg, String result, Object data, int total) {
	super();
	this.msg = msg;
	this.result = result;
	this.rows = data;
	this.total = total;
    }

    public void setSysErrorInfo() {
	this.msg = RESULT_ERROR_MSG;
	this.result = RESULT_ERROR;
    }

    public void setBizErrorInfo(BizException e) {
	this.msg = e.getMessage();
	this.result = RESULT_ERROR;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public Object getRows() {
	return rows;
    }

    public void setRows(Object data) {
	this.rows = data;
    }

    public int getTotal() {
	return total;
    }

    public void setTotal(int total) {
	this.total = total;
    }

}
