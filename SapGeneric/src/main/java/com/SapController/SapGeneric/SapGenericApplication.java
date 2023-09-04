package com.SapController.SapGeneric;

import SapGeneric.Sap;
import Utils.NumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"SapGeneric", "Utils", "Conn", "com.SapController.SapGeneric", "com.SapGeneric.test"})
@SpringBootApplication
public class SapGenericApplication {



	public static void main(String[] args) {
		SpringApplication.run(SapGenericApplication.class, args);

		ControllerTest controllerTest = new ControllerTest();
		controllerTest.testOk();
	}




}
