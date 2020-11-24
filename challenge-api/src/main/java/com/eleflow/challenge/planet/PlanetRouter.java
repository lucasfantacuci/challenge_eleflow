package com.eleflow.challenge.planet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/planet")
public class PlanetRouter {

    private PlanetHandler planetHandler;

    public PlanetRouter(@Autowired PlanetHandler planetHandler) {
        this.planetHandler = planetHandler;
    }

    @PostMapping
    public void createPlanetAsync(@RequestBody CreatePlanetDTO createPlanetDTO){
        this.planetHandler.createPlanet(createPlanetDTO);
    }

    @GetMapping
    public Flux<Planet> findPlanets(@RequestParam(value = "name", defaultValue = "", required = false) String name){
        if (!name.isBlank()) return this.planetHandler.handleFindPlanetsByName(name);
        return this.planetHandler.handleFindPlanetsFromDatabase();
    }

    @GetMapping("/starwars-api")
    public Mono<PlanetsFromApiDTO> findPlanetsFromAPI(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page){
        return this.planetHandler.handleFindPagedPlanetsFromAPI(page);
    }

    @GetMapping("/{id}")
    public Mono<Planet> findPlanetById(@PathVariable("id") String id){
        return this.planetHandler.handleFindPlanetsById(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePlanetById(@PathVariable("id") String id){
        return this.planetHandler.handleDeletePlanetById(UUID.fromString(id));
    }

}