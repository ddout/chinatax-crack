package com.cdhy.ei.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cdhy.commons.utils.ParamsUtil;
import com.cdhy.commons.utils.crack.HttpApi;
import com.cdhy.commons.utils.crack.JSRunner;
import com.cdhy.commons.utils.crack.SwjgUtil;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_01;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_02;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_03;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_04;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_10;
import com.cdhy.commons.utils.crack.fpinfo.FpInfoUtil_11;
import com.cdhy.commons.utils.exception.BizException;
import com.cdhy.ei.service.IChinataxCrackService;

import net.sf.json.JSONObject;

@Service
public class ChinataxCrackServiceImpl implements IChinataxCrackService {
    private static final Logger log = Logger.getLogger(ChinataxCrackServiceImpl.class);

    @Override
    public Map<String, Object> checkBy(Map<String, Object> parm) {
	Map<String, Object> returnMap = new HashMap<String, Object>();
	String fpdm = ParamsUtil.getString4Map(parm, "fpdm").trim();// 代码
	//
	if ("".equals(fpdm)) {
	    throw new BizException("发票代码不能为空");
	}
	Map<String, Object> result = getYzm(fpdm);
	String resultStr = ParamsUtil.getString4Map(result, "result");
	JSONObject json = JSONObject.fromObject(resultStr);
	String key1 = json.getString("key1");
	switch (key1) {
	case "003":
	    throw new BizException("验证码请求次数过于频繁，请1分钟后再试！");
	case "005":
	    throw new BizException("非法请求!");
	case "010":
	    throw new BizException("网络超时，请重试！(01)");
	case "fpdmerr":
	    throw new BizException("请输入合法发票代码!");
	case "024":
	    throw new BizException("24小时内验证码请求太频繁，请稍后再试！");
	case "016":
	    throw new BizException("服务器接收的请求太频繁，请稍后再试！");
	default:
	    break;
	}
	returnMap.put("yzm", json);
	//

	return returnMap;
    }

    @Override
    public Map<String, Object> check(Map<String, Object> parm) {
	Map<String, Object> returnMap = new HashMap<String, Object>();
	//
	String fpdm = ParamsUtil.getString4Map(parm, "fpdm").trim();// 代码
	String fphm = ParamsUtil.getString4Map(parm, "fphm").trim();// 号码
	String kprq = ParamsUtil.getString4Map(parm, "kprq").trim();// 开票日期
	String fpje = ParamsUtil.getString4Map(parm, "fpje").trim();// 金额
	String fpjym = ParamsUtil.getString4Map(parm, "fpjym").trim();// 校验码
	String fp_key = "";
	//
	if ("".equals(fpdm) || "".equals(fphm) || "".equals(kprq)) {
	    throw new BizException("发票代码,发票号码,开票日期 不能为空");
	}
	String yzm = ParamsUtil.getString4Map(parm, "yzm").trim();// 图片验证码
	String yzm_key2 = ParamsUtil.getString4Map(parm, "yzm_key2").trim();// 验证码key2
	String yzm_key3 = ParamsUtil.getString4Map(parm, "yzm_key3").trim();// 验证码key3
	if ("".equals(yzm) || "".equals(yzm_key3) || "".equals(yzm_key2)) {
	    throw new BizException("验证码信息不全");
	}
	// 判断发票类型--确定输入参数中-金额还是验证码
	String fpType = getFPType(fpdm);
	if ("01".equals(fpType) || "02".equals(fpType) || "03".equals(fpType) || "99".equals(fpType)) {
	    // 填金额
	    if ("".equals(fpje)) {
		throw new BizException("金额 不能为空");
	    }
	    fp_key = fpje;
	} else if ("04".equals(fpType) || "10".equals(fpType) || "11".equals(fpType)) {
	    // 填校验码
	    if ("".equals(fpjym)) {
		throw new BizException("校验码 不能为空");
	    }
	    if (fpjym.length() < 6) {
		throw new BizException("校验码 不正确");
	    } else if (fpjym.length() > 6) {
		fpjym = fpjym.substring(fpjym.length() - 6);
	    }
	    fp_key = fpjym;
	}
	// 判断发票类型--确定输入参数中-金额还是验证码
	// 获取发票
	//
	JSONObject keyJson = JSONObject.fromObject(JSRunner.newInstance().getStoreKey());
	//
	Map<String, Object> checkParm = new HashMap<String, Object>();
	checkParm.put("fpdm", fpdm);
	checkParm.put("fphm", fphm);
	checkParm.put("kprq", kprq);
	checkParm.put("fpje", fp_key);
	checkParm.put("fplx", fpType);
	checkParm.put("yzm_key2", yzm_key2);
	checkParm.put("yzm_key3", yzm_key3);
	checkParm.put("iv", keyJson.getString("iv"));
	checkParm.put("salt", keyJson.getString("salt"));
	checkParm.put("yzm", yzm);
	String resultStr = getFp(checkParm, null);
	// 解析结果--有非常多的情况
	JSONObject fpJSON = JSONObject.fromObject(resultStr);
	log.debug(fpJSON);
	String cyjgdm = fpJSON.getString("key1");
	log.debug(cyjgdm);
	switch (cyjgdm) {
	case "1":
	    throw new BizException("该省尚未开通发票查验功能！");// 标志为1，是试运行且代码为非北京，上海，深圳的。
	case "001":
	    // 正常的；
	    break;
	case "002":
	    throw new BizException("超过该张发票当日查验次数(请于次日再次查验)!");
	case "003":
	    throw new BizException("发票查验请求太频繁，请稍后再试！");
	case "004":
	    throw new BizException("超过服务器最大请求数，请稍后访问!");
	case "005":
	    throw new BizException("请求不合法!");
	case "006":
	    throw new BizException("查验发票信息不一致");
	case "007":
	    throw new BizException("验证码失效!");
	case "008":
	    throw new BizException("验证码错误!");
	case "009":
	    throw new BizException("查无此票");
	case "010":
	    throw new BizException("网络超时，请重试！error!");
	case "010_":
	    throw new BizException("网络超时，请重试！(05)");
	case "020":
	    throw new BizException("由于查验行为异常，涉嫌违规，当前无法使用查验服务！");
	case "rqerr":
	    throw new BizException("当日开具发票可于次日进行查验！");
	default:
	    throw new BizException("网络超时，请重试！(04)");
	}
	// keyJson,fpType,fpJSON
	// 开始解析数据
	returnMap = parseFPInfo(fpJSON, checkParm);
	log.debug(returnMap);
	// 开始解析数据
	return returnMap;
    }

    /**
     * 解析回来的发票数据
     * 
     * @param fpJSON
     * @param checkParm
     */
    private Map<String, Object> parseFPInfo(JSONObject fpJSON, Map<String, Object> checkParm) {
	// String iv = ParamsUtil.getString4Map(checkParm, "iv");//
	// String salt = ParamsUtil.getString4Map(checkParm, "salt");//
	String fplx = ParamsUtil.getString4Map(checkParm, "fplx");// 发票类型
	String fpdm = ParamsUtil.getString4Map(checkParm, "fpdm");//
	String fphm = ParamsUtil.getString4Map(checkParm, "fphm");//
	String yzm_key2 = ParamsUtil.getString4Map(checkParm, "yzm_key2");//
	//
	String key2 = fpJSON.getString("key2");// N▽南通市人民东路887号尚东国际5幢1501室
	String key3 = fpJSON.getString("key3");// 税□控□系□统□技□术□维□护□费□█ █年█6
	String key4 = fpJSON.getString("key4");// Iv3AuHeVtYgigFQ0WO6y/St7
	// String key5 = fpJSON.getString("key5");// var fpxx=fpdm+'≡'+
	// String key6 = fpJSON.getString("key6");// var
	// result={\"template\":0,\
	String key7 = fpJSON.getString("key7");// 196aafa4d90f475b09ff3a50722afd7f
	String key8 = fpJSON.getString("key8");// 8807be39948fa4ff5dbd91a9a610d7bd
	String key9 = fpJSON.getString("key9");// 337b6cc5bc46b34c3628365ed634ffec
	String key10 = fpJSON.getString("key10");// T74KP3SWf8Cwj7KC1x
	String key11 = fpJSON.getString("key11");// dc1de
	//
	// 解密备注,解密排序顺序
	String keyStr = JSRunner.newInstance().getloadKey(key7, key8, key9, key4, key10);
	// {"jmbz":"539906315665\n2017-2-22至2018-2-21","jmsort":"2_3_15_4_1_5_14_6_7_9_8_16_10_11_13_12_0"}
	JSONObject keyJson = JSONObject.fromObject(keyStr);
	String jmbz = keyJson.getString("jmbz");// 解密备注信息
	String jmsort = keyJson.getString("jmsort");// 解密排序顺序
	String ruleStr = JSRunner.newInstance().getUrlJS("https://inv-veri.chinatax.gov.cn/js/" + key11 + ".js");// 拿到解密传
	// 组装发票信息--加密的
	JSONObject fp_Info = new JSONObject();
	fp_Info.put("template", "0");// 这个需要在解析一下
	fp_Info.put("fplx", fplx);
	key2 = fpdm + "≡" + fphm + "≡" + SwjgUtil.getSwjgReginName(fpdm) + "≡" + key2 + "≡" + yzm_key2;
	fp_Info.put("fpxx", key2);
	fp_Info.put("hwxx", key3);
	fp_Info.put("jmbz", jmbz);
	fp_Info.put("sort", jmsort);
	fp_Info.put("ruleStr", ruleStr);
	fp_Info.put("fpdm", fpdm);
	fp_Info.put("fphm", fphm);
	fp_Info.put("yzm_key2", yzm_key2);
	log.debug(fp_Info);
	// 01=增值税专用发票,02=货物运输业增值税专用发票(没有了),03=机动车销售统一发票,04=增值税普通发票,10=增值税电子普通发票,
	// 11=增值税普通发票（卷票）
	switch (fplx) {
	case "01":// 增值税专用发票**
	    return FpInfoUtil_01.parseFPInfo(fp_Info);
	case "02":// 货物运输业增值税专用发票**
	    return FpInfoUtil_02.parseFPInfo(fp_Info);
	case "03":// 机动车销售统一发票**
	    return FpInfoUtil_03.parseFPInfo(fp_Info);
	case "04":// 增值税普通发票***
	    return FpInfoUtil_04.parseFPInfo(fp_Info);
	case "10":// 增值税电子普通发票***
	    return FpInfoUtil_10.parseFPInfo(fp_Info);
	case "11":// 增值税普通发票（卷票）
	    return FpInfoUtil_11.parseFPInfo(fp_Info);
	default:
	    return new HashMap<String, Object>();
	}
    }

    // -------------------------------------

    @SuppressWarnings("deprecation")
    private static String getFp(Map<String, Object> checkParm, Header[] headers) {
	String fpdm = ParamsUtil.getString4Map(checkParm, "fpdm");
	String fphm = ParamsUtil.getString4Map(checkParm, "fphm");
	String kprq = ParamsUtil.getString4Map(checkParm, "kprq");
	String fpje = ParamsUtil.getString4Map(checkParm, "fpje");
	String fplx = ParamsUtil.getString4Map(checkParm, "fplx");
	String yzm_key2 = ParamsUtil.getString4Map(checkParm, "yzm_key2");
	String yzm_key3 = ParamsUtil.getString4Map(checkParm, "yzm_key3");
	String iv = ParamsUtil.getString4Map(checkParm, "iv");
	String salt = ParamsUtil.getString4Map(checkParm, "salt");
	String yzm = ParamsUtil.getString4Map(checkParm, "yzm");

	String url = "https://fpdk.jsgs.gov.cn:80/WebQuery/query?callback=jQuery11";
	JSONObject json = SwjgUtil.getSwjg(fpdm);
	if (null != json) {
	    url = json.getString("Ip") + "/WebQuery/query?callback=jQuery11";
	}
	String param = "";
	param += "fpdm=" + fpdm + "&fphm=" + fphm + "&kprq=" + kprq + "&fpje=" + fpje + "&fplx=" + fplx + "&yzmSj="
		+ java.net.URLEncoder.encode(yzm_key2);
	param += "&index=" + yzm_key3 + "&iv=" + iv + "&salt=" + salt;
	param += "&_=" + System.currentTimeMillis();
	param += "&yzm=" + yzm;
	//
	url += "&" + param;
	Map<String, Object> o = HttpApi.getUrl(url, null, headers);
	String a = o.get("result").toString();
	a = a.substring(9, a.length() - 1);
	log.debug(a);
	return a;
    }

    private static Map<String, Object> getYzm(String fpdm) {
	String url = "https://fpdk.jsgs.gov.cn:80/WebQuery/yzmQuery?fpdm=" + fpdm + "&t=" + System.currentTimeMillis();
	JSONObject json = SwjgUtil.getSwjg(fpdm);
	if (null != json) {
	    url = json.getString("Ip") + "/WebQuery/yzmQuery?fpdm=" + fpdm + "&t=" + System.currentTimeMillis();
	}
	Map<String, Object> o = HttpApi.getUrl(url, null);
	return o;
    }

    private static List<String> code = Arrays
	    .asList(new String[] { "144031539110", "131001570151", "133011501118", "111001571071" });

    /**
     * 获取发票类型
     * 
     * @param fpdm
     * @return 01=增值税专用发票,02=货物运输业增值税专用发票(没有了),03=机动车销售统一发票,04=增值税普通发票,10=
     *         增值税电子普通发票, 11=增值税普通发票（卷票）
     */
    public static String getFPType(String a) {
	String c = "99";
	if (a.length() == 12) {
	    String b = a.substring(7, 8);
	    if (code.contains(a)) {
		c = "10";
	    } else {
		if ("0".equals(a.substring(0, 1)) && "11".equals(a.substring(10, 12))) {
		    // 判断是否为新版电子票
		    c = "10";
		} else if ("0".equals(a.substring(0, 1))
			&& ("06".equals(a.substring(10, 12)) || "07".equals(a.substring(10, 12)))) {
		    // 判断是否为卷式发票 第1位为0且第11-12位为06或07
		    c = "11";
		} else if ("2".equals(b) && !"0".equals(a.substring(0, 1))) {
		    // 第8位是2，则是机动车发票
		    c = "03";
		}
	    }
	} else if (a.length() == 10) {
	    String b = a.substring(7, 8);
	    if ("1".equals(b) || "5".equals(b)) {
		c = "01";
	    } else if ("6".equals(b) || "3".equals(b)) {
		c = "04";
	    } else if ("7".equals(b) || "2".equals(b)) {
		c = "02";
	    }
	} else {
	    throw new BizException("发票代码不正确");
	}
	return c;
    }

}
