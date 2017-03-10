package com.cdhy.commons.utils.framework;

import org.apache.commons.codec.binary.Base64;

import com.emeey.core.utils.StringKit;

public class Base64Util {

    private static final Base64 base64 = new Base64();
    public static final String CONST_EMPTY_STRING = "";

    public static final String Base64Encode(String origin) {

	try {
	    if (StringKit.isEmptyString(origin)) {
		return CONST_EMPTY_STRING;
	    }
	    return Base64Encode(origin.getBytes("utf-8"));
	} catch (Exception e) {
	    return null;
	}
    }

    public static final String Base64Encode(byte[] bytes) {
	if (bytes == null || bytes.length <= 0) {
	    return CONST_EMPTY_STRING;
	}
	try {
	    return new String(base64.encode(bytes), "utf-8");
	} catch (Exception e) {
	    return CONST_EMPTY_STRING;
	}
    }

    public static final String Base64Decode(String origin) {
	try {
	    if (StringKit.isEmptyString(origin)) {
		return CONST_EMPTY_STRING;
	    }
	    return Base64Decode(origin.getBytes("utf-8"));
	} catch (Exception e) {
	    return null;
	}
    }

    public static final String Base64Decode(byte[] bytes) {
	try {
	    byte[] decodeBytes = base64.decode(bytes);
	    return new String(decodeBytes, "utf-8");
	} catch (Exception e) {
	    return CONST_EMPTY_STRING;
	}
    }
    public static void main(String[] args) {
	System.out.println(Base64Encode("tamid=1&tamid=2阿里斯柯达加拉时间段拉开手机端绿卡精神可嘉拉开手机端绿卡见识到了哭敬爱是凉快的加拉克家大开杀戒"));
	System.out.println(Base64Decode(Base64Encode("tamid=1&tamid=2")));
    }
}
