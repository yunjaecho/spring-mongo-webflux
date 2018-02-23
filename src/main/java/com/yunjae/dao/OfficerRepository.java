package com.yunjae.dao;

import com.yunjae.entities.Officer;
import com.yunjae.entities.Rank;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Created by USER on 2018-02-23.
 */
public interface OfficerRepository extends ReactiveCrudRepository<Officer, String> {
    Flux<Officer> findByRank(@Param("rank") Rank rank);
    Flux<Officer> findByLast(@Param("last") String last);
}
