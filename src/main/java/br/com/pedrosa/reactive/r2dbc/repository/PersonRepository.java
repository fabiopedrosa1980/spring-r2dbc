package br.com.pedrosa.reactive.r2dbc.repository;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

}
