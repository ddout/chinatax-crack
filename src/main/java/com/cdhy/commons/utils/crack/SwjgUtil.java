package com.cdhy.commons.utils.crack;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SwjgUtil {
    public static final String str = ""
	    + "[{'code':'1100','sfmc':'北京','Ip':'https://zjfpcyweb.bjsat.gov.cn:443','address':'https://zjfpcyweb.bjsat.gov.cn:443'},"
	    + "{'code':'1200','sfmc':'天津','Ip':'https://fpcy.tjsat.gov.cn:443','address':'https://fpcy.tjsat.gov.cn:443'},"
	    + "{'code':'1300','sfmc':'河北','Ip':'https://fpcy.he-n-tax.gov.cn:82','address':'https://fpcy.he-n-tax.gov.cn:82'},"
	    + "{'code':'1400','sfmc':'山西','Ip':'https://fpcy.tax.sx.cn:443','address':'https://fpcy.tax.sx.cn:443'},"
	    + "{'code':'1500','sfmc':'内蒙古','Ip':'https://fpcy.nm-n-tax.gov.cn:443','address':'https://fpcy.nm-n-tax.gov.cn:443'},"
	    + "{'code':'2100','sfmc':'辽宁','Ip':'https://fpcy.tax.ln.cn:443','address':'https://fpcy.tax.ln.cn:443'},"
	    + "{'code':'2102','sfmc':'大连','Ip':'https://fpcy.dlntax.gov.cn:443','address':'https://fpcy.dlntax.gov.cn:443'},"
	    + "{'code':'2200','sfmc':'吉林','Ip':'https://fpcy.jl-n-tax.gov.cn:4432','address':'https://fpcy.jl-n-tax.gov.cn:4432'},"
	    + "{'code':'2300','sfmc':'黑龙江','Ip':'https://fpcy.hl-n-tax.gov.cn:443','address':'https://fpcy.hl-n-tax.gov.cn:443'},"
	    + "{'code':'3100','sfmc':'上海','Ip':'https://fpcyweb.tax.sh.gov.cn:1001','address':'https://fpcyweb.tax.sh.gov.cn:1001'},"
	    + "{'code':'3200','sfmc':'江苏','Ip':'https://fpdk.jsgs.gov.cn:80','address':'https://fpdk.jsgs.gov.cn:80'},"
	    + "{'code':'3300','sfmc':'浙江','Ip':'https://fpcyweb.zjtax.gov.cn:443','address':'https://fpcyweb.zjtax.gov.cn:443'},"
	    + "{'code':'3302','sfmc':'宁波','Ip':'https://fpcy.nb-n-tax.gov.cn:443','address':'https://fpcy.nb-n-tax.gov.cn:443'},"
	    + "{'code':'3400','sfmc':'安徽','Ip':'https://fpcy.ah-n-tax.gov.cn:443','address':'https://fpcy.ah-n-tax.gov.cn:443'},"
	    + "{'code':'3500','sfmc':'福建','Ip':'https://fpcyweb.fj-n-tax.gov.cn:443','address':'https://fpcyweb.fj-n-tax.gov.cn:443'},"
	    + "{'code':'3502','sfmc':'厦门','Ip':'https://fpcy.xm-n-tax.gov.cn','address':'https://fpcy.xm-n-tax.gov.cn'},"
	    + "{'code':'3600','sfmc':'江西','Ip':'https://fpcy.jxgs.gov.cn:82','address':'https://fpcy.jxgs.gov.cn:82'},"
	    + "{'code':'3700','sfmc':'山东','Ip':'https://fpcy.sd-n-tax.gov.cn:443','address':'https://fpcy.sd-n-tax.gov.cn:443'},"
	    + "{'code':'3702','sfmc':'青岛','Ip':'https://fpcy.qd-n-tax.gov.cn:443','address':'https://fpcy.qd-n-tax.gov.cn:443'},"
	    + "{'code':'4100','sfmc':'河南','Ip':'https://fpcy.ha-n-tax.gov.cn','address':'https://fpcy.ha-n-tax.gov.cn'},"
	    + "{'code':'4200','sfmc':'湖北','Ip':'https://fpcy.hb-n-tax.gov.cn:443','address':'https://fpcy.hb-n-tax.gov.cn:443'},"
	    + "{'code':'4300','sfmc':'湖南','Ip':'https://fpcy.hntax.gov.cn:8083','address':'https://fpcy.hntax.gov.cn:8083'},"
	    + "{'code':'4400','sfmc':'广东','Ip':'https://fpcy.gd-n-tax.gov.cn:443','address':'https://fpcy.gd-n-tax.gov.cn:443'},"
	    + "{'code':'4403','sfmc':'深圳','Ip':'https://fpcy.szgs.gov.cn:443','address':'https://fpcy.szgs.gov.cn:443'},"
	    + "{'code':'4500','sfmc':'广西','Ip':'https://fpcy.gxgs.gov.cn:8200','address':'https://fpcy.gxgs.gov.cn:8200'},"
	    + "{'code':'4600','sfmc':'海南','Ip':'https://fpcy.hitax.gov.cn:443','address':'https://fpcy.hitax.gov.cn:443'},"
	    + "{'code':'5000','sfmc':'重庆','Ip':'https://fpcy.cqsw.gov.cn:80','address':'https://fpcy.cqsw.gov.cn:80'},"
	    + "{'code':'5100','sfmc':'四川','Ip':'https://fpcy.sc-n-tax.gov.cn:443','address':'https://fpcy.sc-n-tax.gov.cn:443'},"
	    + "{'code':'5200','sfmc':'贵州','Ip':'https://fpcy.gz-n-tax.gov.cn:80','address':'https://fpcy.gz-n-tax.gov.cn:80'},"
	    + "{'code':'5300','sfmc':'云南','Ip':'https://fpcy.yngs.gov.cn:443','address':'https://fpcy.yngs.gov.cn:443'},"
	    + "{'code':'5400','sfmc':'西藏','Ip':'https://fpcy.xztax.gov.cn:81','address':'https://fpcy.xztax.gov.cn:81'},"
	    + "{'code':'6100','sfmc':'陕西','Ip':'https://fpcyweb.sn-n-tax.gov.cn:443','address':'https://fpcyweb.sn-n-tax.gov.cn:443'},"
	    + "{'code':'6200','sfmc':'甘肃','Ip':'https://fpcy.gs-n-tax.gov.cn:443','address':'https://fpcy.gs-n-tax.gov.cn:443'},"
	    + "{'code':'6300','sfmc':'青海','Ip':'https://fpcy.qh-n-tax.gov.cn:443','address':'https://fpcy.qh-n-tax.gov.cn:443'},"
	    + "{'code':'6400','sfmc':'宁夏','Ip':'https://fpcy.nxgs.gov.cn:443','address':'https://fpcy.nxgs.gov.cn:443'},"
	    + "{'code':'6500','sfmc':'新疆','Ip':'https://fpcy.xj-n-tax.gov.cn:443','address':'https://fpcy.xj-n-tax.gov.cn:443'}]";
    public static final JSONArray swjg_arr = JSONArray.fromObject(str);

    public static JSONObject getSwjg(String fpdm) {
	String dqdm = "";
	if (fpdm.length() == 12) {
	    dqdm = fpdm.substring(1, 5);
	} else {
	    dqdm = fpdm.substring(0, 4);
	}
	if (dqdm != "2102" && dqdm != "3302" && dqdm != "3502" && dqdm != "3702" && dqdm != "4403") {
	    dqdm = dqdm.substring(0, 2) + "00";
	}
	for (int i = 0; i < swjg_arr.size(); i++) {
	    JSONObject info = swjg_arr.getJSONObject(i);
	    if (info.getString("code").equals(dqdm)) {
		return info;
	    }
	}
	return null;
    }

    public static String getSwjgReginName(String fpdm) {
	JSONObject json = getSwjg(fpdm);
	if (null == json) {
	    return "";
	} else {
	    return json.getString("sfmc");
	}
    }
}
