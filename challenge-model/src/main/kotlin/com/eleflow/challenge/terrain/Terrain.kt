package com.eleflow.challenge.terrain

class Terrain (name: String) {

    private val name : String = name;

    init { if (name.isNullOrEmpty()) throw TerrainNameIsEmptyException(); }

    fun getName(): String {
        return this.name;
    }

}