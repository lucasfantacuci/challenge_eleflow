package com.eleflow.challenge.planet

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.io.Serializable
import java.math.BigInteger
import java.util.*

@Table("PLANET")
class PlanetEntity (id: UUID, name: String, numberOfMoviesShowed: BigInteger, climate: String, terrain: String): Serializable {

    @Column("ID") @PrimaryKey var id : UUID = id;
    @Column("NAME") var name : String = name;
    @Column("NUMBER_OF_MOVIES_SHOWED") var numberOfMoviesShowed : BigInteger = numberOfMoviesShowed;
    @Column("CLIMATE") var climate : String = climate;
    @Column("TERRAIN") var terrain : String = terrain;

}
