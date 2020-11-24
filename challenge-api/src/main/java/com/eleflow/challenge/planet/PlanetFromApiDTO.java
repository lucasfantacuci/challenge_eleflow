package com.eleflow.challenge.planet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlanetFromApiDTO {
    @JsonProperty ("name") public String name;
    @JsonProperty ("rotation_period") public String rotationPeriod;
    @JsonProperty ("orbital_period") public String orbitalPeriod;
    @JsonProperty ("diameter") public String diameter;
    @JsonProperty ("climate") public String climate;
    @JsonProperty ("gravity") public String gravity;
    @JsonProperty ("terrain") public String terrain;
    @JsonProperty ("surface_water") public String surfaceWater;
    @JsonProperty ("population") public String population;
    @JsonProperty ("residents") public List<String> residents;
    @JsonProperty ("films") public List<String> films;
    @JsonProperty ("created") public String created;
    @JsonProperty ("edited") public String edited;
    @JsonProperty ("url") public String url;

}