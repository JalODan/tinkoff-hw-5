package kz.oj.tinkoffhw5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TinkoffHw5Application {

	public static void main(String[] args) {
		SpringApplication.run(TinkoffHw5Application.class, args);
	}
}
