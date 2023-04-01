package org.myproject.uam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.myproject.uam.dto.Request;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class AdminUamApplication  {

	public static void main(String[] args) {
		SpringApplication.run(AdminUamApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		ObjectMapper objectMapper=new ObjectMapper();
//	   LocalDate localDate=LocalDate.now();
//
//		Request request=new Request();
//		System.out.println(objectMapper.writeValueAsString(request));

//	}
}
