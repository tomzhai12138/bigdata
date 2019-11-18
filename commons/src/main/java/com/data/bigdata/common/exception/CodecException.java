package com.data.bigdata.common.exception;

import com.data.bigdata.common.HighdsaException;

/**
 * 
 * @ClassName:  CodecException   
 * @Description: 编码解码异常 
 * @author: zhaiyongji
 * @date:   2019年8月14日 上午9:39:37   
 *     
 * @Copyright: © 2019 版权所有
 */
public class CodecException extends HighdsaException{
	private static final long serialVersionUID = 6017110757917075553L;

	public CodecException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CodecException(int errorCode, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(errorCode, message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public CodecException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * @param errorCode
	 * @param message
	 */
	public CodecException(int errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public CodecException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CodecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CodecException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CodecException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CodecException(Throwable cause) {
		super(cause);
	}
}