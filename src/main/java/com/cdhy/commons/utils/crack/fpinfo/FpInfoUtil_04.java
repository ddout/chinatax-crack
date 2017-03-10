package com.cdhy.commons.utils.crack.fpinfo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdhy.commons.utils.MoneyUpperUtil;

import net.sf.json.JSONObject;

public class FpInfoUtil_04 {
    public static Map<String, Object> parseFPInfo(JSONObject fp_Info) {
	Map<String, Object> fp_map = new HashMap<String, Object>();
	String template = fp_Info.getString("template");
	String fplx = fp_Info.getString("fplx");
	String fpxx = fp_Info.getString("fpxx");
	String hwxx = fp_Info.getString("hwxx");
	String jmbz = fp_Info.getString("jmbz");
	String sort = fp_Info.getString("sort");
	String rule = fp_Info.getString("ruleStr");
	//
	String[] rules = rule.split("☺");
	String splitstr = rules[0];
	//
	String fpxxs = fpxx.replaceAll(splitstr, "≡");
	String hwxxs = hwxx.replaceAll(splitstr, "≡");
	splitstr = "≡";
	String[] sortarray = sort.split("_");
	String[] tmpfpxx = fpxxs.split("≡");
	String cysj = tmpfpxx[tmpfpxx.length - 1];// 查验时间
	String[] tmpfp = new String[tmpfpxx.length - 4];
	for (int i = 3; i < tmpfpxx.length - 1; i++) {
	    tmpfp[i - 3] = tmpfpxx[i];
	}
	String[] newfpxx = new String[tmpfpxx.length - 4];
	for (int i = 0; i < tmpfpxx.length - 4; i++) {
	    int idx = Integer.parseInt(sortarray[i]);
	    if (idx < tmpfp.length) {
		newfpxx[i] = tmpfp[idx];
	    } else {
		newfpxx[i] = "";
	    }
	}
	String newfpxxstr = tmpfpxx[0] + "≡" + tmpfpxx[1] + "≡" + tmpfpxx[2] + "≡";
	for (int i = 0; i < newfpxx.length; i++) {
	    newfpxxstr = newfpxxstr + newfpxx[i] + "≡";
	}
	fpxxs = newfpxxstr + cysj;// 发票解密数据
	int cycs = 0;// 查验次数
	if (fpxxs != null && !"".equals(fpxxs)) {
	    String[] fpxxArr = fpxxs.split("≡");
	    cycs = Integer.parseInt(fpxxArr[3]) + 1;

	    // 发票数据
	    String fpdm = "" + fpxxArr[0];
	    fp_map.put("fp_dm", fpdm);// 代码
	    String fphm = "" + fpxxArr[1];
	    fp_map.put("fp_hm", fphm);// 号码
	    String fp_title = "" + fpxxArr[2];
	    fp_map.put("title", fp_title);// 标题
	    String cycsStr = "" + cycs;
	    fp_map.put("cycs", cycsStr);// 查验次数
	    String cysjStr = "" + fpxxArr[21];
	    fp_map.put("cysj", cysjStr);// 查验时间
	    //
	    String kprq = "" + formatKprq(fpxxArr[4], rules[3]);
	    fp_map.put("kprq", kprq);// fpxxArr[3]//开票日期
	    String jqbh = "" + fpxxArr[17];
	    fp_map.put("jqbh", jqbh);// 机器编号
	    //
	    String xfmc = "" + fpxxArr[5];
	    fp_map.put("xsf_mc", xfmc);// 销售方名称
	    String xfsbh = "" + formatSBH(fpxxArr[6], rules[1]);
	    fp_map.put("xsf_nsrsbh", xfsbh);// 销售方税号
	    String xfdzdh = "" + fpxxArr[7];
	    fp_map.put("xsf_dzdh", xfdzdh);// 销售方电话地址
	    String xfyhzh = "" + fpxxArr[8];
	    fp_map.put("xsf_yhzh", xfyhzh);// 销售方名称银行账号
	    //
	    String gfmc = "" + fpxxArr[9];
	    fp_map.put("gmf_mc", gfmc);// 购买方名称
	    String gfsbh = "" + fpxxArr[10];
	    fp_map.put("gmf_nsrsbh", gfsbh);// 购买方税号
	    String gfdzdh = "" + fpxxArr[11];
	    fp_map.put("gmf_dzdh", gfdzdh);// 购买方电话地址
	    String gfyhzh = "" + fpxxArr[12];
	    fp_map.put("gmf_yhzh", gfyhzh);// 购买方名称银行账号
	    //
	    String hjje = getMoney(fpxxArr[19], rules[2]);
	    fp_map.put("hjje", hjje);// 合计金额
	    String hjse = getMoney(fpxxArr[14], rules[2]);
	    fp_map.put("hjse", hjse);// 合计税额
	    String jshj = getMoney(fpxxArr[15], rules[2]);
	    fp_map.put("jshj", jshj);// 价税合计
	    String jshj_dx = MoneyUpperUtil.toBigAmt(Double.parseDouble(jshj));
	    fp_map.put("jshj_dx", jshj_dx);// 价税合计大写
	    String bz = "" + jmbz;
	    fp_map.put("bz", bz);// 备注
	    String jym = "" + fpxxArr[13];
	    fp_map.put("jym", jym);// 校验码

	    // 解析item-商品信息
	    List<Map<String, Object>> item_xmxx = parseItem_10(hwxxs, rules[4], rules[2]);
	    fp_map.put("common_fpkj_xmxx", item_xmxx);
	}
	return fp_map;
    }

    private static List<Map<String, Object>> parseItem_10(String hwxxs, String hwstr, String string2) {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	if (null == hwxxs || "".equals(hwxxs)) {
	    return list;
	}
	// 先拆分需要显示明细的货物，如果有要显示明细的，在页面显示一个链接（链接并不是落在某个货物上！），点击连接后新窗口中显示那些h1开头的明细信息。
	String[] hwii = hwxxs.split("▄");
	if (hwii.length > 1) {
	    hwxxs = hwii[0];
	}
	String[] hwinfo = hwxxs.split("≡");
	for (int i = 0; i < hwinfo.length; i++) {
	    String[] hw = hwinfo[i].split("█");
	    Map<String, Object> item = new HashMap<String, Object>();
	    String xmmc = hw[0];
	    if (null != xmmc && !"".equals(xmmc)) {
		xmmc = xmmc.replaceAll(hwstr, "");
	    }
	    item.put("xmmc", xmmc);// 名称--有特殊字符
	    item.put("ggxh", hw[1]);// 规格型号
	    item.put("dw", hw[2]);// 单位
	    item.put("xmsl", hw[6]);// 数量
	    item.put("xmdj", hw[4]);// 单价
	    item.put("xmje", hw[5]);// 金额
	    item.put("sl", hw[3]);// 税率--换算一下
	    item.put("se", hw[7]);// 税额
	    list.add(item);
	}
	return list;
    }

    private static String formatKprq(String time, String add) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	try {
	    Date d = sdf.parse(time);
	    d.setDate(d.getDate() + (0 - Integer.parseInt(add)));
	    return sdf.format(d);
	} catch (ParseException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}

    }

    private static String formatSBH(String sbh, String str) {
	String[] s1 = str.split("_");
	for (int i = 0; i < s1.length; i++) {
	    sbh = chgchar(sbh, s1[i]);
	}
	return sbh;
    }

    private static String chgchar(String nsrsbh, String ss) {
	String a = ss.charAt(2) + "";
	String b = ss.charAt(0) + ""; // 反向替换，所以和java中是相反的
	nsrsbh = nsrsbh.replaceAll(a, "#");
	nsrsbh = nsrsbh.replaceAll(b, "%");
	nsrsbh = nsrsbh.replaceAll("#", b);
	nsrsbh = nsrsbh.replaceAll("%", a);
	return nsrsbh;
    }

    private static String getMoney(String je, String ss) {
	if (null == je || "".equals(je.trim())) {
	    return "";
	}
	double arg1 = Double.parseDouble(je.trim());
	int r1, r2;
	if (arg1 == Math.floor(arg1)) {
	    r1 = 0;
	} else {
	    r1 = ("" + arg1).split("\\.")[1].length();
	}
	double arg2 = Double.parseDouble(ss.trim());
	if (arg2 == Math.floor(arg2)) {
	    r2 = 0;
	} else {
	    r2 = ("" + arg2).toString().split("\\.")[1].length();
	}
	double m = Math.pow(10, Math.max(r1, r2));
	// alert(m);
	double r = (arg1 * m + arg2 * m) / m;
	BigDecimal rb = new BigDecimal(r, MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_DOWN)
		.stripTrailingZeros();
	return rb.doubleValue() + "";
    }
}
