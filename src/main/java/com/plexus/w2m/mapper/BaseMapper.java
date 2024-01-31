package com.plexus.w2m.mapper;

import org.mapstruct.Context;

import java.util.List;

public interface BaseMapper<E, M> {
	M entityToDTO(E e, @Context AvoidRecursivityContext context);

	E dtoToEntity(M dto, @Context AvoidRecursivityContext context);

	List<M> listEntityToDTO(List<E> entities, @Context AvoidRecursivityContext context);

	List<E> listDTOToEntity(List<M> entities, @Context AvoidRecursivityContext context);
}
