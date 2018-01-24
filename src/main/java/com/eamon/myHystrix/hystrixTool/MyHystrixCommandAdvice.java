package com.eamon.myHystrix.hystrixTool;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

import lombok.Data;

/**
 * @description 
 * @author EamonKang	
 * @Date 2018年1月24日
 */
@Data
public class MyHystrixCommandAdvice {
	private String groupName;
	private String commandName;
	private String threadKey;
	private int timeOutSecond;
	private boolean timeOutEnable;

	public Object runCommand(final ProceedingJoinPoint pjp) {
		return wrapWithHystrixCommnad(pjp).execute();
	}

	private HystrixCommand<Object> wrapWithHystrixCommnad(final ProceedingJoinPoint pjp) {
		return new HystrixCommand<Object>(setter()) {
			@Override
			protected Object run() throws Exception {
				try {
					return pjp.proceed();
				} catch (Throwable throwable) {
					throw (Exception) throwable;
				}
			}

			@Override
			protected Object getFallback() {
				return "ERROR";
			}
		};
	}

	private HystrixCommand.Setter setter() {
		HystrixCommand.Setter setter = HystrixCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
				.andCommandKey(HystrixCommandKey.Factory.asKey(commandName))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadKey));
		HystrixCommandProperties.Setter commandPropertiesDefaults = HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(timeOutEnable)
				.withExecutionTimeoutInMilliseconds(timeOutSecond * 1000);
		setter.andCommandPropertiesDefaults(commandPropertiesDefaults);
		return setter;
	}

}
