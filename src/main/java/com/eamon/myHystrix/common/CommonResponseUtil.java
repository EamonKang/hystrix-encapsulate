package com.eamon.myHystrix.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonResponseUtil {
	
	public static void writeResponse(HttpServletRequest request, HttpServletResponse response,String content) throws Exception{
	
		response.setContentType("text/html;charSet=UTF-8");
		response.setHeader("CONTENT_TYPE", "text/html;charSet=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			response.getWriter().print(content);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void writeResponse(HttpServletRequest request,HttpServletResponse response,String content,String etag) throws Exception{
	
		try {
			response.getWriter().print(content);
			content = null;
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
}
