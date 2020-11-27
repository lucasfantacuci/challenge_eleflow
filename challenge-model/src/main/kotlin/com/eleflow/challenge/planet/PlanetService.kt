package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.query.CassandraPageRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.util.*

@Service
class PlanetService {

    private val planetRepository : PlanetRepository;

    constructor(@Autowired planetRepository: PlanetRepository){
        this.planetRepository = planetRepository;
    }

    fun createPlanet(name: String, numberOfMoviesShowed: BigInteger, climate: Climate, terrain: Terrain) {
        val planet = Planet(name, numberOfMoviesShowed, climate, terrain);
        this.planetRepository.save(PlanetMapper().toEntity(planet)).subscribe();
    }

    fun findAll(page: Int, size: Int): Flux<Slice<Planet>> {
        val pageRequest = PageRequest.of(page, size);
        return this.planetRepository
                .findAllPaged(pageRequest).map(PlanetMapper()::toModelSlice);
    }

    fun findByName(name: String, page: Int, size: Int): Flux<Slice<Planet>> {
        val pageRequest = PageRequest.of(page, size);
        return this.planetRepository
            .findByName(name, pageRequest).map(PlanetMapper()::toModelSlice);
    }

    fun find(id: UUID): Mono<Planet> {
        return this.planetRepository.findById(id).map(PlanetMapper()::toModel);
    }

    fun deletePlanet(id: UUID) : Mono<Void> {
        return this.planetRepository.deleteById(id);
    }

}