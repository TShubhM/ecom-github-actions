package com.mongo.Ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//echo "# ecom-github-actions" >> README.md
//		git init
//		git add README.md
//		git commit -m "first commit"
//		git branch -M main
//		git remote add origin https://github.com/TShubhM/ecom-github-actions.git
//		git push -u origin main

@SpringBootApplication
@RestController
public class EcomApplication {

	@GetMapping("/welcome")
	public String welcome(){
		return "Welcome to github actions";
	}
	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}

}
