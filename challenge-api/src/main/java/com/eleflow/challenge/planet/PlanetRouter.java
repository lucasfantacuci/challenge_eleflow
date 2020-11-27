package com.eleflow.challenge.planet;

import com.eleflow.challenge.common.PageFromStarWarsApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/planet")
public class PlanetRouter {

    private PlanetHandler planetHandler;

    public PlanetRouter(@Autowired PlanetHandler planetHandler) {
        this.planetHandler = planetHandler;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void createPlanetAsync(@RequestBody @NotNull @Valid CreatePlanetDTO createPlanetDTO){
        this.planetHandler.handleCreatePlanet(createPlanetDTO);
    }

    @GetMapping
    public Flux<Slice<Planet>> findPlanets(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                           @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                           @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        if (!name.isBlank()) return this.planetHandler.handleFindPlanetsByName(name, page, size);
        return this.planetHandler.handleFindPlanetsFromDatabase(page, size);
    }

    @GetMapping("/starwars-api")
    public Mono<PageFromStarWarsApiDTO<PlanetFromApiDTO>> findPlanetsFromAPI(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page){
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
