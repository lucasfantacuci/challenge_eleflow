package com.eleflow.challenge.planet

import org.springframework.data.cassandra.core.query.CassandraPageRequest
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
    fun findByName(name: String, page: CassandraPageRequest) : Flux<Slice<PlanetEntity>>;

    @AllowFiltering
    @Query("select * from planet")
    fun findAll(page: Pageable): Flux<Slice<PlanetEntity>>
}