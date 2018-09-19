package com.demo.flux;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LogServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(LogServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * URLDecoder decode目的是为了把URL的拉丁编码转换成utf-8,可读
		 * req.getQueryString() 是为了能够得到URL中?后的那一部分
		 * 该方法只对链接拼的字符串有效,对于Post提交的参数,是无法得到的
		 */
		String qs = URLDecoder.decode(req.getQueryString(), "utf-8");
		//根据tongji.js文件提交的格式,&是每个参数的分隔符
		String[] kvs = qs.split("\\&");
		StringBuilder sb = new StringBuilder();
		//把需要的数据提取出来,用|进行分隔
		//注意:此处分隔符不应含有数据中有的字符,前后端需要协调一致
		for(String kv : kvs ){
			String [] arr = kv.split("=");
			String v = arr.length >= 2 ? arr[1] : "";
			sb.append(v+"|");
		}
		//拼接客户端ip,通过后端获取客户端的IP地址
		sb.append(req.getRemoteAddr());
		
		String line = sb.toString();
		logger.info(line);//将整理好的数据输出到控制台和flume中
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
