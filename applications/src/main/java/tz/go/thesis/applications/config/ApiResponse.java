package tz.go.thesis.applications.config;


import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ToString
public class ApiResponse {

	@Autowired
	private MessageSource messageSource;

	private Locale currentLocale = LocaleContextHolder.getLocale();

	public ApiResponseEntity<Object> getResponse(String trxId, Object data, ResponseEnum response) {
		return this.createResponse(trxId, null, data, response);// new ApiResponseEntity<Object>(resp,
																// HttpStatus.valueOf(httpCode));
	}

	public ApiResponseEntity<Object> getResponse(String trxId, String message, Object data, ResponseEnum response) {
		return this.createResponse(trxId, message, data, response);
	}

	public ApiResponseEntity<Object> getResponse(Object data, ResponseEnum response) {
 
		String trxId = UUID.randomUUID().toString();
		return this.createResponse(trxId, null, data, response);
	}
	
	public ApiResponseEntity<Object> getResponse(String message, ResponseEnum response) {
				return this.createResponse(null, message, null, response);
	}

	private ApiResponseEntity<Object> createResponse(String trxId, String message, Object data, ResponseEnum response) {

		String code = messageSource.getMessage("lbl.resp." + response, null, currentLocale);
 
		String status = messageSource.getMessage("lbl.status." + code, null, currentLocale);
		String statusCode = messageSource.getMessage("lbl.code." + code, null, currentLocale);

		String statusMsg = null;

		if (message == null) {
			statusMsg = messageSource.getMessage("lbl.message." + code, null, currentLocale);
		} else {
			statusMsg = message;
		}

		int httpCode = Integer.parseInt(messageSource.getMessage("lbl.httpStatus." + code, null, currentLocale));

		MajiReponse resp = new MajiReponse(status, trxId, statusCode, statusMsg, data);

		return new ApiResponseEntity<Object>(resp, HttpStatus.valueOf(httpCode));
	}

	 
	@Getter @Setter
	@ToString
	public class MajiReponse {

		private String status;

		private String trxnId;

		private String statusCode;

		private String message;

		private Object data;

		public <T> MajiReponse(String status, String trxId, String statusCode, String message, T data) {
			super();
			this.status = status;
			this.trxnId = trxId;
			this.statusCode = statusCode;
			this.message = message;
			this.setData(data);
 
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTrxId() {
			return trxnId;
		}

		public void setTrxId(String trxId) {
			this.trxnId = trxId;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
 
	}

	@ToString
	public class ApiResponseEntity<T> extends ResponseEntity<T> {

		public ApiResponseEntity(T resp, HttpStatus valueOf) {
			super(resp, valueOf);
		}

	}

}
