package com.eamon.myHystrix.hystrixTool;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.eamon.myHystrix.common.BaseResponse;
import com.eamon.myHystrix.common.CommonResponseUtil;
import com.google.gson.Gson;

/**
 * @description 
 * @author EamonKang	
 * @Date 2018年1月24日
 */
@Aspect
@Component
public class MyHystrixCommandAspect {

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	MyHystrixCommandAdvice hystrixCommandAdvice = new MyHystrixCommandAdvice();

	String methodName = "";

	Gson gson = new Gson();

	@Pointcut("@annotation(com.ec.common.hystrix.MyHystrixCommand)")
	public void requestMethod() {

	};

	@Around("requestMethod()")
	public void around(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
		MyHystrixCommand action = method.getAnnotation(MyHystrixCommand.class);
		hystrixCommandAdvice.setCommandName(action.commandKey());
		hystrixCommandAdvice.setGroupName(action.groupKey());
		hystrixCommandAdvice.setThreadKey(action.threadPoolKey());
		hystrixCommandAdvice.setTimeOutSecond(action.timeOutSecond());
		hystrixCommandAdvice.setTimeOutEnable(action.timeOutEnabled());
		
		Object object = hystrixCommandAdvice.runCommand(joinPoint);
		
		if ("ERROR".equals((String)object)) {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			HttpServletResponse response = attributes.getResponse();
			String requestJson = request.getParameter("data");
			JSONObject jsonObject = JSONObject.parseObject(requestJson);
			String lang = jsonObject.getString("lang");
			BaseResponse responseVO = new BaseResponse();
			String description = "请求超时，稍后重试";
			responseVO.outPutFailedResponse("errorCode1", description);
			responseVO.setExpendField("这是断路器返回的");
			String responseJson = gson.toJson(responseVO);
			try {
				CommonResponseUtil.writeResponse(request, response, responseJson);
			} catch (Exception e) {
			}
		}
	}
}
