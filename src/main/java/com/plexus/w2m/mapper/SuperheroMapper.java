package com.plexus.w2m.mapper;

import com.plexus.w2m.dto.SuperheroDTO;
import com.plexus.w2m.entities.Superhero;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true)
		, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SuperheroMapper extends BaseMapper<Superhero, SuperheroDTO> {
}
