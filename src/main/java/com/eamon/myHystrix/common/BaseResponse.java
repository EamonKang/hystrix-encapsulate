package com.eamon.myHystrix.common;

import java.io.Serializable;

/**
 * @author 000901
 * @date 2017年6月6日 
 */
public class BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 6594705512357404577L;
	
    public static final String RESPONSE_SUCCESS = "Y";
    public static final String RESPONSE_FAILED = "N";
    //接口耗费时间
    private long usedTime;
    //请求相应状态返回（Y:N）
  	private String ifSuccess;
  	//请求相应返回描述
  	private String description;
  	//扩展冗余字段
  	private String expendField;
  	//异常编码
  	private String errorCode;
	
	public BaseResponse(){
		super();
	}
		
	
	public BaseResponse(String ifSuccess, String description,
            String expendField, String errorCode)
    {
        super();
        this.ifSuccess = ifSuccess;
        this.description = description;
        this.expendField = expendField;
        this.errorCode = errorCode;
    }
	
	/**
	 * 异常返回方法
	* @Title: outPutFailed  
	* @Description:   异常返回方法
	* @param @param errorCode
	* @param @param description
	* @param @return
	* @return BaseResponse
	* @throws
	 */
	public static BaseResponse outPutFailed(String errorCode,String description){
	    return new BaseResponse(RESPONSE_FAILED,description,"",errorCode); 
	}
	
	/**
	 * 请求正确返回方法
	* @Title: outPutSuccess  
	* @Description: 请求正确返回方法
	* @param @param description
	* @param @return
	* @return BaseResponse
	* @throws
	 */
	public static BaseResponse outPutSuccess(String description){
        return new BaseResponse(RESPONSE_SUCCESS,description,"",""); 
    }
	
	/**
	 * 请求失败返回实例方法
	 * @param errorCode
	 * @param description
	 */
	public void outPutFailedResponse(String errorCode,String description){
		this.ifSuccess = RESPONSE_FAILED;
        this.description = description;
        this.expendField = "";
        this.errorCode = errorCode;
	}
	
	/**
	 * 请求正确返回实例方法
	 * @param description
	 */
	public void outPutSuccessResponse(String description){
		this.ifSuccess = RESPONSE_SUCCESS;
        this.description = description;
        this.expendField = "";
        this.errorCode = "";
	}
	
	public void getBaseResponseParam(BaseResponse baseResponse) {
		this.ifSuccess = baseResponse.getIfSuccess();
		this.errorCode = baseResponse.getErrorCode();
		this.description = baseResponse.getDescription();
	}


	/**
	 * @return the usedTime
	 */
	public long getUsedTime() {
		return usedTime;
	}


	/**
	 * @param usedTime the usedTime to set
	 */
	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}


	/**
	 * @return the ifSuccess
	 */
	public String getIfSuccess() {
		return ifSuccess;
	}


	/**
	 * @param ifSuccess the ifSuccess to set
	 */
	public void setIfSuccess(String ifSuccess) {
		this.ifSuccess = ifSuccess;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the expendField
	 */
	public String getExpendField() {
		return expendField;
	}


	/**
	 * @param expendField the expendField to set
	 */
	public void setExpendField(String expendField) {
		this.expendField = expendField;
	}


	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}


	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
