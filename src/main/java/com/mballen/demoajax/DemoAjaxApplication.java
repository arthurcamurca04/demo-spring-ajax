package com.mballen.demoajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mballen.demoajax.web.model.SocialMetaTag;
import com.mballen.demoajax.web.service.SocialMetaTagService;

@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoAjaxApplication.class, args);
	}

	@Autowired
	SocialMetaTagService service;
	@Override
	public void run(String... args) throws Exception {
		SocialMetaTag og = service.getOpenGraphByUrl("https://www.udemy.com/spring-boot-mvc-com-spring-security/");
		System.out.println(og.toString());
	}

}