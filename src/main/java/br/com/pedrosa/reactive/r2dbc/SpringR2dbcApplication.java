package br.com.pedrosa.reactive.r2dbc;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import br.com.pedrosa.reactive.r2dbc.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Log4j2
@EnableTransactionManagement
public class SpringR2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringR2dbcApplication.class, args);
	}

	@Autowired
	private PersonRepository personRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void ready() throws Exception {

		this.personRepository
				.deleteAll()
				.thenMany(Flux
						.just(new Person(null,"Fabio",39), new Person(null,"Maria",69))
						.flatMap(person -> this.personRepository.save(person)))
				.thenMany(this.personRepository.findAll())
				.subscribe(log::info);
	}

}
