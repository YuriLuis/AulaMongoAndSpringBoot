package com.yuri.luis.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.yuri.luis.model.User;
import com.yuri.luis.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;

	@Override
	public void run(String... args) throws Exception {
		userReposiroty.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com", "123456789");
		User alex = new User(null, "Alex Green", "alex@gmail.com", "123456789");
		User bob = new User(null, "Bob Grey", "bob@gmail.com", "123456789");

		userReposiroty.saveAll(Arrays.asList(maria, alex, bob));
	}

}
