package com.eleflow.challenge.planet;


import com.eleflow.challenge.climate.Climate;
import com.eleflow.challenge.terrain.Terrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class PlanetHandler {

    private PlanetService planetService;
    private PlanetApiIntegrator planetApiIntegrator;

    public PlanetHandler(@Autowired PlanetService planetService,
                         @Autowired PlanetApiIntegrator planetApiIntegrator) {
        this.planetService = planetService;
        this.planetApiIntegrator = planetApiIntegrator;
    }

    public void createPlanet(CreatePlanetDTO createPlanetDTO) {

        Mono<BigInteger> monoNumberOfMoviesShowed = this.planetApiIntegrator.findHowManyMoviesTheNamedPlanetWasShowed(createPlanetDTO.name);

        monoNumberOfMoviesShowed.subscribe(numberOfMoviesShowed -> {
            planetService.createPlanet(
                createPlanetDTO.name,
                numberOfMoviesShowed,
                new Climate(createPlanetDTO.climate),
                new Terrain(createPlanetDTO.terrain));
        });
    }

    public Flux<Planet> handleFindPlanetsFromDatabase(){
        return this.planetService.findAll();
    }

    public Mono<PlanetsFromApiDTO> handleFindPagedPlanetsFromAPI(Integer page) {
        return this.planetApiIntegrator.findPaged(page);
    }

    public Flux<Slice<Planet>> handleFindPlanetsByName(String name, Integer page, Integer size) {
        return this.planetService.findByName(name, page, size);
    }

    public Mono<Planet> handleFindPlanetsById(UUID id) {
        return this.planetService.find(id);
    }

    public Mono<Void> handleDeletePlanetById(UUID id) {
        return this.planetService.deletePlanet(id);
    }
}
