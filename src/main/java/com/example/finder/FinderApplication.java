package com.example.finder;

import com.example.finder.dao.UserRepository;
import com.example.finder.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FinderApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FinderApplication.class, args);

		LoadExampleData loadExampleData = context.getBean(LoadExampleData.class);
		loadExampleData.run();
	}

}
