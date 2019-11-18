package com.data.bigdata.common;

/**
 * 
 * @ClassName:  HighdsaException   
 * @Description:  系统异常基类 
 * @author: zhaiyongji
 * @date:   2019年8月14日 上午9:38:49   
 *     
 * @Copyright: © 2019 版权所有
 */
public class HighdsaException extends RuntimeException {
	private static final long serialVersionUID = 7462624691843766045L;
	
	/** 错误编码 */
	private int errorCode;

	public HighdsaException() {
		super();
	}

	/* ----------------------- added constructors ---------------------------*/
	
	/**
	 * @param errorCode
	 * @param cause
	 */
	public HighdsaException(int errorCode,Throwable cause) {
		super(cause);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 */
	public HighdsaException(int errorCode,String message) {
		super(message);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public HighdsaException(int errorCode,String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public HighdsaException(int errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/* ----------------------- origin constructors ---------------------------*/
	
	/**
	 * @param cause
	 */
	public HighdsaException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 */
	public HighdsaException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public HighdsaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public HighdsaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/* ----------------------- setter/getter ---------------------------*/
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}