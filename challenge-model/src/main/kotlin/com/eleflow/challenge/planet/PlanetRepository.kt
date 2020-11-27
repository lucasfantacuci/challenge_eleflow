package com.eleflow.challenge.planet

import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*


@Repository
interface PlanetRepository : ReactiveCassandraRepository<PlanetEntity, UUID> {

    @AllowFiltering
    fun findByName(name: String, page: Pageable) : Flux<Slice<PlanetEntity>>;

    @AllowFiltering
    @Query("select * from planet")
    fun findAllPaged(page: Pageable): Flux<Slice<PlanetEntity>>
}