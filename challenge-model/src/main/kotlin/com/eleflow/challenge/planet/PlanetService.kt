package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.util.*

@Service
class PlanetService {

    private var planetRepository : PlanetRepository;

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
            planet -> mapper.toModel(planet)
        };
    }

    fun findByName(name: String): Flux<Planet> {
        val mapper = PlanetMapper();
        return this.planetRepository.findByName(name).map { planet -> mapper.toModel(planet) };
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