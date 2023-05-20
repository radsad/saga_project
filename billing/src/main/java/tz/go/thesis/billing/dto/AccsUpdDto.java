package tz.go.thesis.billing.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccsUpdDto {
	private String accountNumber;
	private int status;
	private LocalDateTime deletedAt;
}
