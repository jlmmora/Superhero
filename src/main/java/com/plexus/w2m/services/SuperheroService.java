package com.plexus.w2m.services;

import com.plexus.w2m.dto.SuperheroDTO;
import com.plexus.w2m.entities.Superhero;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuperheroService {
    
    List<SuperheroDTO> findAll();

    SuperheroDTO findById(Long id);
    List<SuperheroDTO> findByName(String name);

    SuperheroDTO save(Superhero superhero);
    
    SuperheroDTO update(Superhero superhero);

    void delete(Long id);


    @Transactional(readOnly = true)
    List<SuperheroDTO> findByNameQuery(@NotNull String name);
}
