package cn.com.datou.smile.common.exception;

public interface IErrorCode {
	/**
	 * 正常响应
	 */
	String SUCCESS = "0000|success";
	/**
	 * 系统异常(中)
	 */
	String SYS_EXCEPTION = "9999|unexpected error";
	/**
	 * 系统异常(英)
	 */
	String SYSTEM_EXCEPTION = "9999|unexpected error";
	/**
	 * 系统维护
	 */
	String SCHEDULED_DOWNTIME = "9998|issuer scheduled downtime";	
	/**
	 * 缓存配置错误
	 */
	String REDIS_ERROR = "9996|config error";
	/**
	 * 重复请求
	 */
	String DUPLICATED_REQUEST = "0001|duplicated request";
	/**
	 * 畸形的json字符串
	 */
	String MALFORMED_JSON = "0002|malformed json";
	/**
	 * 缺失必填参数
	 */
	String MISSING_REQUIRED_FIELD = "0003|missing required field-";
	/**
	 * 记录不存在
	 */
	String RECORD_NOT_EXIST = "0004|record not exist";
}
