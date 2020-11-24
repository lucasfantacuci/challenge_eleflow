package com.eleflow.challenge.terrain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TerrainTest {

    @Test
    fun givenANewTerrainInstance_whenTheTerrainNameIsEmpty_shouldThrowTerrainNameIsEmptyException() {
        Assertions.assertThrows(TerrainNameIsEmptyException::class.java) { Terrain("") }
    }

    @Test
    fun givenANewTerrainInstance_whenTheTerrainNameIsCorrect_shouldCreateTheInstance(){
        val desert = "desert";
        val terrain = Terrain(desert)
        Assertions.assertEquals(desert, terrain.getName());
    }
}