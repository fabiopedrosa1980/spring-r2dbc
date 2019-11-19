package br.com.pedrosa.reactive.r2dbc.repository;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class NativeQueryPersonRepository {
    private final DatabaseClient databaseClient;

    public Flux<Person> all() {
        return databaseClient.execute("SELECT id, name, age FROM PERSON")
                        .as(Person.class)
                        .fetch()
                        .all();
    }

    public Mono<Person> findById(Integer id) {
        return databaseClient.select()
                .from(Person.class)
                .matching(where("id").is(id))
                .as(Person.class)
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Person not found")));
    }

    public Mono<Void> delete(Integer id) {
        return databaseClient.delete()
            .from(Person.class)
            .matching(where("id").is(id))
            .then();
    }

    public Mono<Person> insert(Person person) {
        return databaseClient.execute("INSERT INTO PERSON (name, age) VALUES( :name, :age)")
                .bind("name", person.getName())
                .bind("age", person.getAge())
                .fetch().rowsUpdated()
                .then(Mono.just(person));
    }

    public Mono<Person> update(Integer id,Person person) {
        return databaseClient.update()
                .table("PERSON")
                .using(Update.update("name", person.getName()))
                .matching(where("id").is(id))
                .fetch().rowsUpdated()
                .then(Mono.just(person));

    }
}
