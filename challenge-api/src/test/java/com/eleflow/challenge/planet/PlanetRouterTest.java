package com.eleflow.challenge.planet;

import com.eleflow.challenge.climate.Climate;
import com.eleflow.challenge.terrain.Terrain;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.times;

@WebFluxTest(controllers = PlanetRouter.class)
public class PlanetRouterTest {

    @MockBean
    private PlanetHandler mockedPlanetHandler;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void givenTheDataToCreateANewPlanet_whenInstantiatingIt_shouldExecuteSuccessfully(){

        CreatePlanetDTO createPlanetDTO = new CreatePlanetDTO();
        createPlanetDTO.name = "Tatooine";
        createPlanetDTO.climate = "arid";
        createPlanetDTO.terrain = "desert";

        Mockito.doNothing().when(mockedPlanetHandler).handleCreatePlanet(Mockito.any());

        webClient.post()
                .uri("/planet")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createPlanetDTO))
                .exchange()
                .expectStatus().isAccepted();

        Mockito.verify(mockedPlanetHandler, times(1)).handleCreatePlanet(Mockito.any());
    }

    @Test
    public void givenTheNullDataToCreateANewPlanet_whenInstantiatingIt_shouldReturnBadRequest(){

        webClient.post()
                .uri("/planet")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void givenTheNullFieldsToCreateANewPlanet_whenInstantiatingIt_shouldReturnBadRequest(){

        CreatePlanetDTO createPlanetDTO = new CreatePlanetDTO();
        createPlanetDTO.name = null;
        createPlanetDTO.climate = null;
        createPlanetDTO.terrain = null;

        Mockito.doNothing().when(mockedPlanetHandler).handleCreatePlanet(Mockito.any());

        webClient.post()
                .uri("/planet")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createPlanetDTO))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void givenTheSearchOnPlanets_whenNoneParameterIsPassed_shouldReturnAllPlanetsPagedFromDatabaseWithTheDefaultsPageSettings() {

        ArrayList<Planet> planetsFromDatabase = new ArrayList<>();
        planetsFromDatabase.add(new Planet("Tatooine", BigInteger.ONE, new Climate("arid"), new Terrain("Desert")));

        SliceImpl<Planet> sliceFromDatabase = new SliceImpl(planetsFromDatabase, CassandraPageRequest.of(0, 10), false);
        Flux fluxSliceOfPlanets = Flux.just(sliceFromDatabase);

        Mockito.when(mockedPlanetHandler.handleFindPlanetsFromDatabase(0, 10))
                .thenReturn(fluxSliceOfPlanets);

        webClient.get()
                .uri("/planet")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(mockedPlanetHandler, times(1)).handleFindPlanetsFromDatabase(0, 10);
    }

    @Test
    public void givenTheSearchOnPlanets_whenOnlyNameIsPassed_shouldReturnAllNamedPlanetsPagedFromDatabaseWithTheDefaultsPageSettings() {

        ArrayList<Planet> planetsFromDatabase = new ArrayList<>();
        planetsFromDatabase.add(new Planet("Tatooine", BigInteger.ONE, new Climate("arid"), new Terrain("Desert")));

        SliceImpl<Planet> sliceFromDatabase = new SliceImpl(planetsFromDatabase, CassandraPageRequest.of(0, 10), false);
        Flux fluxSliceOfPlanets = Flux.just(sliceFromDatabase);

        Mockito.when(mockedPlanetHandler.handleFindPlanetsByName("Tatooine", 0, 10))
                .thenReturn(fluxSliceOfPlanets);

        webClient.get()
                .uri("/planet?name=Tatooine")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(mockedPlanetHandler, times(1)).handleFindPlanetsByName("Tatooine",0, 10);
    }

    @Test
    public void givenTheSearchOnPlanets_whenPaginationSettingsIsPassed_shouldExecuteTheSearchBasedOnThePaginationSettings() {

        ArrayList<Planet> planetsFromDatabase = new ArrayList<>();
        planetsFromDatabase.add(new Planet("Tatooine", BigInteger.ONE, new Climate("arid"), new Terrain("Desert")));

        SliceImpl<Planet> sliceFromDatabase = new SliceImpl(planetsFromDatabase, PageRequest.of(2, 5), false);
        Flux fluxSliceOfPlanets = Flux.just(sliceFromDatabase);

        Mockito.when(mockedPlanetHandler.handleFindPlanetsFromDatabase(2, 5))
                .thenReturn(fluxSliceOfPlanets);

        webClient.get()
                .uri("/planet?page=2&size=5")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(mockedPlanetHandler, times(1)).handleFindPlanetsFromDatabase(2, 5);
    }

    @Test
    public void givenTheSearchOfAPlanet_whenFindingById_shouldReturnThePlanetIfItExistsAndTheIdIsCorrect(){

        UUID uuid = UUID.randomUUID();
        Planet planet = new Planet(uuid, "Tatooine", BigInteger.ONE, new Climate("arid"), new Terrain("Desert"));

        Mockito.when(mockedPlanetHandler.handleFindPlanetsById(uuid)).thenReturn(Mono.just(planet));

        webClient.get()
                .uri(new StringBuilder().append("/planet/").append(uuid.toString()).toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(mockedPlanetHandler, times(1)).handleFindPlanetsById(uuid);
    }

    @Test
    public void givenTheSearchOfAPlanet_whenFindingByIdThatDoesntExists_shouldReturnThePlanetIfItExistsAndTheIdIsCorrect(){

        UUID uuid = UUID.randomUUID();
        Planet planet = new Planet(uuid, "Tatooine", BigInteger.ONE, new Climate("arid"), new Terrain("Desert"));

        Mockito.when(mockedPlanetHandler.handleFindPlanetsById(uuid)).thenReturn(Mono.just(planet));

        webClient.get()
                .uri(new StringBuilder().append("/planet/").append(uuid.toString()).toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(mockedPlanetHandler, times(1)).handleFindPlanetsById(uuid);
    }
}
