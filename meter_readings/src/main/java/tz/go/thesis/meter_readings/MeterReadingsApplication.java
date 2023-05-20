package tz.go.thesis.meter_readings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MeterReadingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterReadingsApplication.class, args);
	}

}
