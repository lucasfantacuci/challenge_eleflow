package com.eleflow.challenge.planet

import com.eleflow.challenge.climate.Climate
import com.eleflow.challenge.terrain.Terrain
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import java.util.stream.Collectors

class PlanetMapper {

    fun toEntity(model: Planet) : PlanetEntity {
        return PlanetEntity(
                model.getId(),
                model.getName(),
                model.getNumberOfMoviesShowed(),
                model.getClimate(),
                model.getTerrain());
    }

    fun toModel(entity: PlanetEntity) : Planet {
        return Planet(
                entity.id,
                entity.name,
                entity.numberOfMoviesShowed,
                Climate(entity.climate),
                Terrain(entity.terrain));
    }

    fun toModelSlice(slice: Slice<PlanetEntity>) : Slice<Planet> {
        return SliceImpl(
                slice.content.stream().map(this::toModel).collect(Collectors.toList()),
                slice.pageable,
                slice.hasNext()
        )
    }

}