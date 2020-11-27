package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.util.*

@ExtendWith(MockitoExtension::class)
class PlanetServiceTest {

    private val mockedPlanetRepository = mock(PlanetRepository::class.java);

    @Test
    fun givenACreationOfPlanetByTheService_whenAllParametersAreCorrect_thenItShouldBeSavedOnDatabaseAndNotThrowAnyException() {

        val uuid = UUID.randomUUID();
        val planetName = "Tatooine";
        val planetNumberOfMoviesShowed = BigInteger.TEN;
        val planetClimate = "arid";
        val planetTerrain = "desert";

        val savedPlanetEntity = Mono.just(PlanetEntity(uuid, planetName, planetNumberOfMoviesShowed, planetClimate, planetTerrain));
        Mockito.`when`(mockedPlanetRepository.save(Mockito.any())).thenReturn(savedPlanetEntity);

        val planetService = PlanetService(mockedPlanetRepository);
        planetService.createPlanet(planetName, planetNumberOfMoviesShowed, Climate(planetClimate), Terrain(planetTerrain));

    }

    @Test
    fun givenTheFindOnAllPlanets_whenAllParametersAreCorrect_thenItShouldReturnThePlanetsSaved(){

        val planets = ArrayList<PlanetEntity>();
        planets.add(PlanetEntity(UUID.randomUUID(), "Tatooine", BigInteger.ONE, "arid", "desert"));
        planets.add(PlanetEntity(UUID.randomUUID(), "Naboo", BigInteger.TWO, "humid", "florest"));

        val slice = SliceImpl(planets)

        val fluxFromDatabase = Flux.just(slice);

        Mockito.`when`(mockedPlanetRepository.findAllPaged(PageRequest.of(0, 10))).thenReturn(fluxFromDatabase as Flux<Slice<PlanetEntity>>);

        val planetService = PlanetService(mockedPlanetRepository);
        val fluxOfPlanetsSlice = planetService.findAll(0, 10);

        fluxOfPlanetsSlice.subscribe {
            planetsSlice ->
            run {
                Assertions.assertEquals(2, planetsSlice.size)
            }
        }
    }

    @Test
    fun givenTheFindNamedPlanets_whenAllParametersAreCorrect_thenItShouldReturnThePlanetsSaved(){

        val planets = ArrayList<PlanetEntity>();
        planets.add(PlanetEntity(UUID.randomUUID(), "Tatooine", BigInteger.ONE, "arid", "desert"));

        val slice = SliceImpl(planets)

        val fluxFromDatabase = Flux.just(slice);

        Mockito.`when`(
            mockedPlanetRepository
                .findByName(
                    "Tatooine",
                        PageRequest.of(0, 10)))
                .thenReturn(fluxFromDatabase as Flux<Slice<PlanetEntity>>);

        val planetService = PlanetService(mockedPlanetRepository);
        val fluxOfPlanetsSlice = planetService.findByName("Tatooine", 0, 10);

        fluxOfPlanetsSlice.subscribe {
            planetsSlice ->
            run {
                Assertions.assertEquals(1, planetsSlice.size)
            }
        }
    }

    @Test
    fun givenTheFindById_whenAllParametersAreCorrect_thenItShouldReturnThePlanetSaved(){

        val uuid = UUID.randomUUID();
        val planetName = "Tatooine";
        val planetNumberOfMoviesShowed = BigInteger.TEN;
        val planetClimate = "arid";
        val planetTerrain = "desert";

        Mockito.`when`(
                mockedPlanetRepository
                    .findById(uuid))
                .thenReturn(
                    Mono.just(
                        PlanetEntity(
                            uuid,
                            planetName,
                            planetNumberOfMoviesShowed,
                            planetClimate,
                            planetTerrain)));

        val planetService = PlanetService(mockedPlanetRepository);
        val fluxOfPlanetsSlice = planetService.find(uuid);

        fluxOfPlanetsSlice.subscribe {
            planet ->
            run {
                Assertions.assertEquals(uuid, planet.getId())
            }
        }
    }

    @Test
    fun givenTheDeleteById_whenAllParametersAreCorrect_thenItShouldReturnVoid(){

        val uuid = UUID.randomUUID();

        Mockito.`when`(
                mockedPlanetRepository
                        .deleteById(uuid))
                .thenReturn(
                        Mono.empty());

        val planetService = PlanetService(mockedPlanetRepository);
        planetService.deletePlanet(uuid);

    }
}
