package com.example.teleconsultationbackend;

import com.example.teleconsultationbackend.Config.WebMvcConfig;
import com.example.teleconsultationbackend.Config.WebSocketConfig;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication

@Import({WebSocketConfig.class, WebMvcConfig.class})
@OpenAPIDefinition(
		info = @Info(
				title = "TeleConsultation Application REST APIs",
				description = "This App allow user to do realtime consultation at his own convinience",
				version = "v1.0",
				contact = @Contact(
						name = "Team Shunya",
						email = "rushikothri46@gmail.com",
						url = "https://www.linkedin.com/in/rushi-kothari-930413162/"

				)

		),
		externalDocs = @ExternalDocumentation(
				description = "TeleConsultation Application REST APIs",
				url = "https://github.com/pandeyaman2170/HAD_Project_backend"

		)


)
public class TeleConsultationBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(TeleConsultationBackendApplication.class, args);
	}

}
