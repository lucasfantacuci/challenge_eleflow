package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.query.CassandraPageRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.util.*
import java.util.stream.Collectors

@Service
class PlanetService {

    private val planetRepository : PlanetRepository;

    constructor(@Autowired planetRepository: PlanetRepository){
        this.planetRepository = planetRepository;
    }

    fun createPlanet(name: String, numberOfMoviesShowed: BigInteger, climate: Climate, terrain: Terrain) {
        val mapper = PlanetMapper();
        val planet = Planet(name, numberOfMoviesShowed, climate, terrain);
        this.planetRepository.save(mapper.toEntity(planet)).subscribe();
    }

    fun findAll(): Flux<Planet> {
        val mapper = PlanetMapper();

        return this.planetRepository.findAll().map {
            planetEntity -> mapper.toModel(planetEntity)
        };
    }

    fun findByName(name: String, page: Int, size: Int): Flux<Slice<Planet>> {
        val mapper = PlanetMapper();

        val pageRequest = CassandraPageRequest.of(page, size);

        return this.planetRepository
            .findByName(name, pageRequest).map {
                sliceOfPlanetEntity ->
                    SliceImpl(
                        sliceOfPlanetEntity.content.stream().map { planetEntity -> mapper.toModel(planetEntity) }.collect(Collectors.toList()),
                        sliceOfPlanetEntity.pageable,
                        sliceOfPlanetEntity.hasNext()
                    )
            };
    }

    fun find(id: UUID): Mono<Planet> {
        val mapper = PlanetMapper();
        return this.planetRepository.findById(id).map {
            planet -> mapper.toModel(planet);
        }
    }

    fun deletePlanet(id: UUID) : Mono<Void> {
        return this.planetRepository.deleteById(id);
    }
}