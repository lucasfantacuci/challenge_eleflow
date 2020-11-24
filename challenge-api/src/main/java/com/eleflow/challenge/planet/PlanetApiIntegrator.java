package com.eleflow.challenge.planet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.math.BigInteger;
import java.nio.charset.Charset;

@Component
public class PlanetApiIntegrator {

    private String apiStarWarsBaseUrl;

    public PlanetApiIntegrator(@Value("${api.starwars.base-url}") String apiStarWarsEndpoint){
        this.apiStarWarsBaseUrl = apiStarWarsEndpoint;
    }

    public Mono<PlanetsFromApiDTO> findPaged(Integer page) {

        WebClient webClient = this.createReactiveWebClient(this.apiStarWarsBaseUrl);

        return webClient
                .get().uri(new StringBuilder().append("/planets?page=").append(page).toString())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.defaultCharset())
                .retrieve()
                .bodyToMono(PlanetsFromApiDTO.class)
                .map(this::replaceLinkUrl);
    }

    public Mono<BigInteger> findHowManyMoviesTheNamedPlanetWasShowed(String name) {

        WebClient webClient = createReactiveWebClient(this.apiStarWarsBaseUrl);

        return webClient
                .get().uri(new StringBuilder().append("/planets?search=").append(name).toString())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.defaultCharset())
                .retrieve()
                .bodyToMono(PlanetsFromApiDTO.class)
                .map(this::countHowManyMoviesWasShowed);

    }

    private WebClient createReactiveWebClient(String baseUrl) {
        return
            WebClient.builder()
                .clientConnector(
                    new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)))
                .baseUrl(baseUrl)
                .build();
    }

    private PlanetsFromApiDTO replaceLinkUrl(PlanetsFromApiDTO response) {
        if (response.next != null) response.next = response.next.replace("http://swapi.dev/api/planets/", "planets/starwars-api");
        if (response.previous != null) response.previous = response.previous.replace("http://swapi.dev/api/planets/", "planets/starwars-api");
        return response;
    }

    private BigInteger countHowManyMoviesWasShowed(PlanetsFromApiDTO response) {
        if (Integer.valueOf(response.count) >= 1) return BigInteger.valueOf(response.planets.get(0).films.size());
        return BigInteger.ZERO;
    }
}
