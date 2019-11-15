package br.com.pedrosa.reactive.r2dbc;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Log4j2
@EnableTransactionManagement
public class SpringR2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringR2dbcApplication.class, args);
	}

}
