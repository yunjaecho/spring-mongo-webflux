package com.yunjae;

import com.yunjae.dao.OfficerRepository;
import com.yunjae.entities.Officer;
import com.yunjae.entities.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 별도의 Class Component 의 CommandLineRunner
 * Other way : @SpringBootApplication 에 implements ApplicationRunner 구현도 가능어함
 * Created by USER on 2018-02-26.
 */
@Component
public class OfficerInit implements CommandLineRunner {
    @Autowired
    private OfficerRepository dao;

    @Autowired
    private ReactiveMongoOperations operations;

    /*private OfficerRepository dao;
    private ReactiveMongoOperations operations;*/

    /**
     * Construction method Injection
     * same as @Autowired
     * @param dao
     * @param operations
     */
    /*public OfficerInit(OfficerRepository dao, ReactiveMongoOperations operations) {
        this.dao = dao;
        this.operations = operations;
    }*/


    /**
     * mongodb execution is create collection after drop collection
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        operations.collectionExists(Officer.class)
                .flatMap(exists -> exists ? operations.dropCollection(Officer.class) : Mono.just(exists))
                .flatMap(o -> operations.createCollection(Officer.class,
                        CollectionOptions.empty().size(1024 * 1024).maxDocuments(100).capped()))
                .then()
                .block();

        dao.saveAll(Flux.just(new Officer(Rank.CAPTAIN, "James", "Kirk"),
                new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
                new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
                new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
                new Officer(Rank.CAPTAIN, "Jonathan", "Archer")))
                .then()
                .block();
    }
}
