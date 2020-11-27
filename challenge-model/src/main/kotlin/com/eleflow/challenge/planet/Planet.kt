package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain

import java.math.BigInteger
import java.util.*

class Planet (id: UUID, name: String, numberOfMoviesShowed: BigInteger, climate: Climate, terrain: Terrain) {

    private val id : UUID = id;
    private val name : String = name;
    private val numberOfMoviesShowed : BigInteger = numberOfMoviesShowed;
    private val climate : Climate = climate;
    private val terrain : Terrain = terrain;

    init { if (this.name.isNullOrEmpty()) throw PlanetNameIsEmptyException(); }

    constructor(name: String, numberOfMoviesShowed: BigInteger, climate: Climate, terrain: Terrain)
            : this(UUID.randomUUID(), name, numberOfMoviesShowed, climate, terrain);

    fun getId(): UUID {
        return this.id;
    }

    fun getName(): String {
        return this.name;
    }

    fun getNumberOfMoviesShowed(): BigInteger {
        return this.numberOfMoviesShowed;
    }

    fun getClimate(): String {
        return this.climate.getName();
    }

    fun getTerrain(): String {
        return this.terrain.getName();
    }
}