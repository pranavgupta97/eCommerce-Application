package com.example.demo;

import com.splunk.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
@EntityScan("com.example.demo.model.persistence")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SareetaApplication {

    @Bean
    public Service splunkService() {

        Service.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
        ServiceArgs loginArguments = new ServiceArgs();
        loginArguments.setUsername("pranavgupta97");
        loginArguments.setPassword("Awesome123$$");
        loginArguments.setScheme("https");
        loginArguments.setHost("localhost");
        loginArguments.setPort(8089);

        Service service = Service.connect(loginArguments);
        return service;
    }


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SareetaApplication.class, args);
	}

}
