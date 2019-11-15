package br.com.pedrosa.reactive.r2dbc;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import br.com.pedrosa.reactive.r2dbc.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringR2dbcApplicationTests {
	
	@Autowired
	PersonRepository personRepository;

	@Test
	public void whenDeleteAll_then0IsExpected() {
		personRepository.deleteAll()
				.as(StepVerifier::create)
				.expectNextCount(0)
				.verifyComplete();
	}

	@Test
	public void whenInsert6_then6AreExpected() {
		insertPersons();
		personRepository.findAll()
				.as(StepVerifier::create)
				.expectNextCount(6)
				.verifyComplete();
	}

	private void insertPersons() {
		List<Person> players = Arrays.asList(
				new Person(1, "Kaka", 37),
				new Person(2, "Messi", 32),
				new Person(3, "Mbappé", 20),
				new Person(4, "CR7", 34),
				new Person(5, "Lewandowski", 30),
				new Person(6, "Cavani", 32)
		);
		personRepository.saveAll(players).subscribe();
	}

}
