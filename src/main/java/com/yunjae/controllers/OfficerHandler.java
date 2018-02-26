package com.yunjae.controllers;

import com.yunjae.dao.OfficerRepository;
import com.yunjae.entities.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by USER on 2018-02-26.
 */
@Component
public class OfficerHandler {
    @Autowired
    private OfficerRepository repository;

    public Mono<ServerResponse> listOfficers(ServerRequest request) {
        Flux<Officer> officers = repository.findAll();
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(officers, Officer.class);
    }

    public Mono<ServerResponse> createOfficer(ServerRequest request) {
        Mono<Officer> officerMono = request.bodyToMono(Officer.class);
        return officerMono.flatMap(officer ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(repository.save(officer), Officer.class));
    }

    public Mono<ServerResponse> getOfficer(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Officer> personMono = this.repository.findById(id);
        return personMono
                .flatMap(person -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(person)))
                .switchIfEmpty(notFound);

    }
}
