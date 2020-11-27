package com.eleflow.challenge.planet;

import javax.validation.constraints.NotNull;

public class CreatePlanetDTO {

    @NotNull public String name;
    @NotNull public String climate;
    @NotNull public String terrain;

}
