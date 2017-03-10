package com.cdhy.commons.utils.exception;

/**
 * 业务异常
 * 
 * @Copyright (C),沪友科技
 * @author pjf
 * @Date:2015年10月26日
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 5744893024411154342L;

	public BizException() {
		super();
	}

	public BizException(String msg) {
		super(msg);
	}

	public BizException(Throwable e) {
		super(e);
	}

	public BizException(String msg, Throwable e) {
		super(msg, e);
	}
}
