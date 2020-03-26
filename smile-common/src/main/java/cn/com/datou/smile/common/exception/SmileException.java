package cn.com.datou.smile.common.exception;

import lombok.Data;

@Data
public class SmileException extends Exception{
	private static final long serialVersionUID = -3031632114973788193L;

	public SmileException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = message;
	}

	public SmileException(String message) {
		super(message);
		this.errorCode = message;
	}

	private String errorCode;
}
