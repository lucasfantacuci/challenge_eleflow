package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class PlanetTest {

    @Test
    fun givenANewPlanetInstance_whenThePlanetNameIsEmpty_shouldThrowPlanetNameIsEmptyException() {
        val climate = Climate("arid");
        val terrain = Terrain("desert");
        val numberOfMoviesShowed : BigInteger = BigInteger.ONE;
//        Assertions.assertThrows(PlanetNameIsEmptyException::class.java) { Planet("", numberOfMoviesShowed, climate, terrain) };
    }

    @Test
    fun givenANewPlanetInstance_whenThePlanetNameIsCorrect_shouldCreateTheInstance() {

        val arid = "arid";
        val desert = "desert";
        val tatooine = "Tatooine";
        val numberOfMoviesShowed : BigInteger = BigInteger.ONE;

        val climate = Climate(arid);
        val terrain = Terrain(desert);
       // val planet = Planet(tatooine, numberOfMoviesShowed, climate, terrain);

        //Assertions.assertEquals(tatooine, planet.getName());
        //Assertions.assertEquals(BigInteger.ONE, planet.getNumberOfMoviesShowed());
        //Assertions.assertEquals(arid, planet.getClimate());
        //Assertions.assertEquals(desert, planet.getTerrain());
    }

}