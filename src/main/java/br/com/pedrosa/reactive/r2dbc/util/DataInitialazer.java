package br.com.pedrosa.reactive.r2dbc.util;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import br.com.pedrosa.reactive.r2dbc.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Log4j2
@Component
public class DataInitialazer {

    private final PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() throws Exception {
        var persons = Flux
                .just(new Person(null,"Fabio",39), new Person(null,"Maria",69))
                .flatMap(person -> this.personRepository.save(person));

        this.personRepository
                .deleteAll()
                .thenMany(persons)
                .thenMany(this.personRepository.findAll())
                .subscribe(log::info);
    }
}
