package com.eleflow.challenge.climate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ClimateTest {

    @Test
    fun givenANewClimateInstance_whenTheClimateNameIsEmpty_shouldThrowClimateNameIsEmptyException(){
        Assertions.assertThrows(ClimateNameIsEmptyException::class.java) { Climate("") }
    }

    @Test
    fun givenANewClimateInstance_whenTheClimateNameIsCorrect_shouldCreateTheInstance(){
        val arid = "arid";
        val climate = Climate(arid)
        Assertions.assertEquals(arid, climate.getName());
    }
}