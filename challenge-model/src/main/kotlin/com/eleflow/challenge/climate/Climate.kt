package com.eleflow.challenge.climate

class Climate (name: String){

    private val name = name;

    init { if (name.isNullOrEmpty()) throw ClimateNameIsEmptyException(); }

    fun getName(): String {
        return this.name;
    }
}