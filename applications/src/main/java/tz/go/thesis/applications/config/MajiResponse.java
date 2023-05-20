package tz.go.thesis.applications.config;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to carry response when communicating with Nida
*/
@Data
@NoArgsConstructor
public class MajiResponse {
	int statusCode;
	String transactionId;
	String status;
	String message;
	Object data;
	public MajiResponse(int statusCode, String transactionId, String status, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.transactionId = transactionId;
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	
	
}

