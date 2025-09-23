package com.sdas204221.ManageHive;

import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ManageHiveApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ManageHiveApplication.class);
	}

	public static void main(String[] args) {
//		ApplicationContext applicationContext=
				SpringApplication.run(ManageHiveApplication.class, args);
//		User user=applicationContext.getBean(User.class);
//		BCryptPasswordEncoder enc=new BCryptPasswordEncoder(12);
//		user.setUsername("admin");
//		user.setPassword("4321");
//		user.setRole("ADMIN");
//		user.setAccountLocked(false);
//
//		UserService userService=applicationContext.getBean(UserService.class);
//        userService.save(user);
//		userService.givPassword(user,"1234");
//		userService.update(user);
		System.out.println("Server Started");
	}
}

