package com.eamon.myHystrix.hystrixTool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 
 * @author EamonKang	
 * @Date 2018年1月24日
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyHystrixCommand {
	
	/**
	 * Title: groupKey
	 */
	String groupKey() default "my_groupKey";

    /**
     * Title: commandKey
     */
    String commandKey() default "my_commandKey";

    /**
     * Title: threadPoolKey
     */
    String threadPoolKey() default "my_threadPoolKey";

    /**
     * Title: timeOutSecond 用来配置执行的超时时间
     */
    int timeOutSecond() default 30;
    
    /**
     * Title: timeOutEnabled 用来配置是否开启的超时功能
     */
    boolean timeOutEnabled() default true;

}
