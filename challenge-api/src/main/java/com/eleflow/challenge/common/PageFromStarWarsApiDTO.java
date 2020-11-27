package com.eleflow.challenge.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageFromStarWarsApiDTO <T> {

    @JsonProperty ("count") public String count;
    @JsonProperty ("next") public String next;
    @JsonProperty ("previous") public String previous;
    @JsonProperty ("results") public List<T> results;

}


