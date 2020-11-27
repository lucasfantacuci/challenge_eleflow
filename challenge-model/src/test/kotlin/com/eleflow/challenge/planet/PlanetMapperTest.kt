package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.climate.ClimateNameIsEmptyException
import com.eleflow.challenge.terrain.Terrain
import com.eleflow.challenge.terrain.TerrainNameIsEmptyException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.*

class PlanetMapperTest {

    @Test
    fun givenAnInstantiatedPlanetModel_whenMappingToPlanetEntity_shouldMapCorrectlyAndNotChangeTheFieldsValues() {

        val mapper = PlanetMapper();

        val planet = Planet("Tatooine", BigInteger.ONE, Climate("Arid"), Terrain("desert"));

        val planetEntity = mapper.toEntity(planet);

        Assertions.assertEquals(planet.getId(), planetEntity.id);
        Assertions.assertEquals(planet.getName(), planetEntity.name);
        Assertions.assertEquals(planet.getNumberOfMoviesShowed(), planetEntity.numberOfMoviesShowed);
        Assertions.assertEquals(planet.getClimate(), planetEntity.climate);
        Assertions.assertEquals(planet.getTerrain(), planetEntity.terrain);

    }

    @Test
    fun givenAValidatedPlanetEntityInstantiated_whenMappingToPlanetModel_shouldMapCorrectlyAndNotChangeTheFieldsValues() {

        val mapper = PlanetMapper();

        val planetEntity = PlanetEntity(UUID.randomUUID(), "Tatooine", BigInteger.ONE, "arid", "desert");

        val planet = mapper.toModel(planetEntity);

        Assertions.assertEquals(planet.getId(), planetEntity.id);
        Assertions.assertEquals(planet.getName(), planetEntity.name);
        Assertions.assertEquals(planet.getNumberOfMoviesShowed(), planetEntity.numberOfMoviesShowed);
        Assertions.assertEquals(planet.getClimate(), planetEntity.climate);
        Assertions.assertEquals(planet.getTerrain(), planetEntity.terrain);

    }

    @Test
    fun givenANotValidPlanetEntityNameInstantiated_whenMappingToPlanetModel_shouldPlanetNameIsNotValidException() {

        val mapper = PlanetMapper();
        val planetEntity = PlanetEntity(UUID.randomUUID(), "", BigInteger.ONE, "arid", "desert");

        Assertions.assertThrows(PlanetNameIsEmptyException::class.java) {
            mapper.toModel(planetEntity);
        };
    }

    @Test
    fun givenANotValidPlanetEntityClimateInstantiated_whenMappingToPlanetModel_shouldClimateNameIsNotValidException() {

        val mapper = PlanetMapper();
        val planetEntity = PlanetEntity(UUID.randomUUID(), "Tatooine", BigInteger.ONE,"", "desert");

        Assertions.assertThrows(ClimateNameIsEmptyException::class.java) {
            mapper.toModel(planetEntity);
        };
    }

    @Test
    fun givenANotValidPlanetEntityTerrainInstantiated_whenMappingToPlanetModel_shouldTerrainNameIsNotValidException() {

        val mapper = PlanetMapper();
        val planetEntity = PlanetEntity(UUID.randomUUID(), "Tatooine", BigInteger.ONE,"arid", "");

        Assertions.assertThrows(TerrainNameIsEmptyException::class.java) {
            mapper.toModel(planetEntity);
        };
    }
}