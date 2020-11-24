package com.eleflow.challenge.planet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlanetsFromApiDTO {

    @JsonProperty ("count") public String count;
    @JsonProperty ("next") public String next;
    @JsonProperty ("previous") public String previous;
    @JsonProperty ("results") public List<PlanetFromApiDTO> planets;

}


