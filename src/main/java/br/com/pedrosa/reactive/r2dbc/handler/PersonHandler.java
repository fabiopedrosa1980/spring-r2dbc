package br.com.pedrosa.reactive.r2dbc.handler;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import br.com.pedrosa.reactive.r2dbc.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Component
public class PersonHandler {

    private final PersonRepository personRepository;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(this.personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        log.info("save person");
        Mono<Person> operation = req.bodyToMono(Person.class)
                .flatMap(this.personRepository::save);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(operation, Person.class);
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        log.info("update person");
        return this.personRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(personUpdate -> {
                    Person person = req.bodyToMono(Person.class).block();
                    personUpdate.setName(person.getName());
                    personUpdate.setAge(person.getAge());
                    return personUpdate;
                })
                .flatMap(person -> ServerResponse.ok().body(this.personRepository.save(person), Person.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        log.info("get operation");
        return this.personRepository.findById(Long.valueOf(req.pathVariable("id")))
                .flatMap(person -> ServerResponse.ok().body(Mono.just(person), Person.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        log.info("delete person");
        return this.personRepository.findById(Long.valueOf(req.pathVariable("id")))
                .flatMap(person -> ServerResponse.noContent().build(this.personRepository.delete(person)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
