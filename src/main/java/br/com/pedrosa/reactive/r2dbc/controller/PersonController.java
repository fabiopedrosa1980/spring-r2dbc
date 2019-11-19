package br.com.pedrosa.reactive.r2dbc.controller;

import br.com.pedrosa.reactive.r2dbc.domain.Person;
import br.com.pedrosa.reactive.r2dbc.repository.NativeQueryPersonRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("persons")
@Api
public class PersonController {

    private final NativeQueryPersonRepository nativeQueryPersonRepository;

    @GetMapping
    public Flux<Person> all(){
        return this.nativeQueryPersonRepository.all();
    }

    @GetMapping("{id}")
    public Mono<Person> findById(@PathVariable Integer id){
        return this.nativeQueryPersonRepository.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable Integer id){
        return this.nativeQueryPersonRepository.delete(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> insert(@RequestBody Person person){
        return this.nativeQueryPersonRepository.insert(person);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Person> update(@PathVariable Integer id,@RequestBody Person person){
        return this.nativeQueryPersonRepository.update(id,person);
    }
}
