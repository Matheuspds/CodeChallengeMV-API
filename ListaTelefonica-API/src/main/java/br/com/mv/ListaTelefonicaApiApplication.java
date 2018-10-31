package br.com.mv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ListaTelefonicaApiApplication {

	@RequestMapping("/")
    String root() {
        return "<h1>It Works!</h1>";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ListaTelefonicaApiApplication.class, args);
	}
}
