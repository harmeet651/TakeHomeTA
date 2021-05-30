package com.example.demo;

import com.sun.javaws.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URLClassLoader;
import java.util.Arrays;

@SpringBootApplication
public class TakeHomeProjectTrueAccordApplication implements CommandLineRunner {

	@Autowired
	MainService service;

	@Autowired
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		URLClassLoader classLoader = (URLClassLoader) Main.class.getClassLoader();
		System.out.println(Arrays.toString(classLoader.getURLs()));
		SpringApplication.run(TakeHomeProjectTrueAccordApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hi");

		service.displayDebts();
		System.exit(SpringApplication.exit(context));
	}
}
