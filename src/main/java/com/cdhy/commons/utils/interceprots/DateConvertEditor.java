package com.cdhy.commons.utils.interceprots;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;

/**
 * 
 * @author 张代浩
 *
 */
public class DateConvertEditor extends PropertyEditorSupport {
    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void setAsText(String text) throws IllegalArgumentException {

	if (text.length() < 5) {
	    // 补全
	    String[] strArray = text.split(":");
	    if (strArray.length == 2) {

		text = strArray[0].length() == 1 ? "0" + strArray[0] : strArray[0];
		text += ":";
		text += strArray[1].length() == 1 ? "0" + strArray[1] : strArray[1];

	    }
	}

	if (text.length() == 5) {
	    text = "1970-01-01 " + text;
	}

	if (text.length() == 16) {
	    text += ":00";
	}

	if (StringUtils.hasText(text)) {
	    try {
		if (text.indexOf(":") == -1 && text.length() == 10) {
		    setValue(this.dateFormat.parse(text));
		} else if (text.indexOf(":") > 0 && text.length() == 19) {
		    setValue(this.datetimeFormat.parse(text));
		} else if (text.indexOf(":") > 0 && text.length() == 21) {
		    text = text.replace(".0", "");
		    setValue(this.datetimeFormat.parse(text));
		} else {
		    throw new IllegalArgumentException("Could not parse date, date format is error ");
		}
	    } catch (ParseException ex) {
		IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
		iae.initCause(ex);
		throw iae;
	    }
	} else {
	    setValue(null);
	}
    }
}
