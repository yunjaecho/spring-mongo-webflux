package com.yunjae.controllers;

import com.yunjae.dao.OfficerRepository;
import com.yunjae.entities.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by USER on 2018-02-26.
 */
@RestController
@RequestMapping("/officers")
public class OfficerController {
    @Autowired
    private OfficerRepository repository;

    @GetMapping
    public Flux<Officer> getAllOfficers() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Officer> getOfficer(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Officer> saveOfficer(@RequestBody Officer officer) {
        return repository.save(officer);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Officer>> updateOfficer(@PathVariable(value = "id") String id, @RequestBody Officer officer) {
        return repository.findById(id)
                .flatMap(existingOfficer -> {
                    existingOfficer.setRank(officer.getRank());
                    existingOfficer.setFirst(officer.getFirst());
                    existingOfficer.setLast(officer.getLast());
                    return repository.save(existingOfficer);
                })
                .map(updateOfficer -> new ResponseEntity<Officer>(updateOfficer, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Officer>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteOfficer(@PathVariable(value = "id") String id) {

        return repository.findById(id)
                .flatMap(existingOfficer ->
                        repository.delete(existingOfficer)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public Mono<Void> deleteAllOfficers() {
        return repository.deleteAll();
    }

}
