package com.cdhy.commons.utils.crack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.ibatis.io.Resources;

import net.sf.json.JSONObject;

public class JSRunner {
    public static void main(String[] args) {
	// System.out.println(JSRunner.newInstance().getloadKey("c69bf8f902b299c165000fabf174e7c3",
	// "c9bf884cb282e586191dd01a35d6663b",
	// "52100962eaf16e4f519ee70411940ae2",
	// "7185x00QCms6wbt7bR5f2bhCkFpfZhZhqV6wHQk5eBmbPsIOgW+lJLHbWZ0besuh",
	// "KQXDV1tK0xAl0ikYdaFENoALuxUpKW0M8LdnY6dDKl7i2yWjHbb4ZhDfOYRCSZ3s"));
	String a = JSRunner.newInstance().getUrlJS("https://inv-veri.chinatax.gov.cn/js/dc1de.js");
	System.out.println(a);
    }

    //
    private static JSRunner jsRunner = new JSRunner();

    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("javascript");

    public static JSRunner newInstance() {
	return jsRunner;
    }

    private JSRunner() {
    }

    private static final String aes_js = readFileString("conf/js/aes.js");
    private static final String pbkdf2_js = readFileString("conf/js/pbkdf2.js");
    private static final String myScript = readFileString("conf/js/myScript.js");
    private static final String myScript2 = readFileString("conf/js/myScript2.js");

    public String getStoreKey() {
	try {
	    Object o = engine.eval(aes_js + pbkdf2_js + myScript);
	    return JSONObject.fromObject(o).toString();
	} catch (ScriptException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public String getUrlJS(String url) {
	try {
	    String rule = HttpApi.getUrlText(url);
	    System.out.println(rule);
	    String script = "";
	    script += "(function() {";
	    script += rule;
	    script += "return {\"rule\" : rule}})()";
	    Object o = engine.eval(script);
	    return JSONObject.fromObject(o).getString("rule").toString();
	} catch (ScriptException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * 
     * @param jsonData_key7
     *            jsonData_key7
     * @param jsonData_key8
     *            jsonData_key8
     * @param jsonData_key9
     *            jsonData_key9
     * @param jsonData_key4
     *            jsonData_key4
     * @param jsonData_key10
     *            jsonData_key10
     * @return
     */
    public String getloadKey(String jsonData_key7, String jsonData_key8, String jsonData_key9, String jsonData_key4,
	    String jsonData_key10) {
	try {
	    String thisScript = myScript2.replaceAll("jsonData_key7", "\"" + jsonData_key7 + "\"")
		    .replaceAll("jsonData_key8", "\"" + jsonData_key8 + "\"")
		    .replaceAll("jsonData_key9", "\"" + jsonData_key9 + "\"")
		    .replaceAll("jsonData_key4", "\"" + jsonData_key4 + "\"")
		    .replaceAll("jsonData_key10", "\"" + jsonData_key10 + "\"");
	    Object o = engine.eval(aes_js + pbkdf2_js + thisScript);
	    return JSONObject.fromObject(o).toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    private static String readFileString(String filePath) {
	StringBuilder a = new StringBuilder();
	FileInputStream fis = null;
	try {
	    File file = Resources.getResourceAsFile(JSRunner.class.getClassLoader(), filePath);
	    fis = new FileInputStream(file);
	    int len = -1;
	    byte[] b = new byte[1024];
	    while ((len = fis.read(b)) != -1) {
		a.append(new String(b, 0, len));
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}

	return a.toString();
    }

}
