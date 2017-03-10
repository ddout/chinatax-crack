package com.cdhy.commons.utils.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cdhy.commons.utils.exception.BizException;

/**
 * 分页对象
 * 
 * @Copyright (C),沪友科技
 * @author pjf
 * @Date:2015年10月26日
 */
public class Page implements Serializable {

    /**  */
    private static final long serialVersionUID = -3595446750212246959L;
    /** 总行数 */
    private int rowsCount;

    private int start = 0;
    private int limit = 15;

    @SuppressWarnings("rawtypes")
    private List data = new ArrayList();

    public Page() {
    }

    public Page(int start, int limit) {
	super();
	this.start = start;
	this.limit = limit;
    }

    public Page(Object start, Object limit) {
	super();
	try {
	    int st = Integer.parseInt(start.toString());
	    int lt = Integer.parseInt(limit.toString());
	    this.start = st;
	    this.limit = lt;
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	    throw new BizException("分页参数转换错误", e);
	}
    }

    public int getRowsCount() {
	return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
	this.rowsCount = rowsCount;
    }

    public int getStart() {
	if (start == 1) {
	    return 1;
	} else {
	    return (start - 1) * limit + 1;
	}
    }

    public int getEnd() {
	if (start == 1) {
	    return limit;
	} else {
	    return getStart() + limit - 1;
	}
    }

    public void setStart(int start) {
	this.start = start;
    }

    public int getLimit() {
	return limit;
    }

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public List getData() {
	return data;
    }

    public void setData(List data) {
	this.data = data;
    }

}
