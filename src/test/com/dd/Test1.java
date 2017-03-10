package com.dd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:conf/spring*.xml" })
public class Test1 {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
	this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void check() throws Exception {
	ResultActions ra = mockMvc.perform((post("/taxCrackController/check.do").characterEncoding("UTF-8")
		.contentType(MediaType.TEXT_HTML).param("fpdm", "032001600311").param("fphm", "37368100")
		.param("kprq", "20170303").param("fpje", "820.00").param("fpjym", "792541")
		.param("yzm", "的这")
		.param("yzm_key2", "2017-03-09 10:35:23")
		.param("yzm_key3", "edfdab692a963e3e78b16e8306f074ad")
		))
		.andExpect(status().isOk()).andDo(print());
	JSONObject result = JSONObject.fromObject(ra.andReturn().getResponse().getContentAsString());
	System.out.println(result);
    }

    @Test
    public void checkBy() throws Exception {
	ResultActions ra = mockMvc.perform((post("/taxCrackController/checkBy.do").characterEncoding("UTF-8")
		.contentType(MediaType.TEXT_HTML).param("fpdm", "032001600311").param("fphm", "37368100")
		.param("kprq", "20170303").param("fpje", "820.00").param("fpjym", "792541").param("yzm", "控要思")))
		.andExpect(status().isOk()).andDo(print());
	JSONObject result = JSONObject.fromObject(ra.andReturn().getResponse().getContentAsString());
	System.out.println(result);
    }
}
