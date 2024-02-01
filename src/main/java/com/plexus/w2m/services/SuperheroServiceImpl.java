package com.plexus.w2m.services;

import com.plexus.w2m.dto.SuperheroDTO;
import com.plexus.w2m.entities.Superhero;
import com.plexus.w2m.exception.SuperheroNotFoundException;
import com.plexus.w2m.mapper.AvoidRecursivityContext;
import com.plexus.w2m.mapper.SuperheroMapper;
import com.plexus.w2m.repository.SuperheroRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    private SuperheroRepository repository;
    @Autowired
    private SuperheroMapper superheroMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SuperheroDTO> findAll() {
        return superheroMapper.listEntityToDTO(repository.findAll().stream().collect(Collectors.toList()), new AvoidRecursivityContext());
    }

    @Override
    @Transactional(readOnly = true)
    public SuperheroDTO findById(Long id) {
        Optional<Superhero> optionalSuperhero = repository.findById(id);
        if (optionalSuperhero.isPresent()) {
            return superheroMapper.entityToDTO(optionalSuperhero.get(), new AvoidRecursivityContext());
        }
        throw(new SuperheroNotFoundException(id));
    }


    @Override
    @Transactional
    public SuperheroDTO save(Superhero superhero) {
        validations(superhero);
        return superheroMapper.entityToDTO(repository.save(superhero), new AvoidRecursivityContext());
    }

    @Override
    @Transactional
    public SuperheroDTO update(Superhero superhero) {
        validations(superhero);
        Optional<Superhero> optionalSuperhero = repository.findById(superhero.getId());
        if (optionalSuperhero.isPresent()) {
            Superhero superheroDB = optionalSuperhero.get();

            superheroDB.setAbilities(superhero.getAbilities());
            superheroDB.setName(superhero.getName());
            superheroDB.setRealName(superhero.getRealName());
            superheroDB.setPower(superhero.getPower());

            return superheroMapper.entityToDTO(repository.save(superhero), new AvoidRecursivityContext());

        }
        return superheroMapper.entityToDTO(optionalSuperhero.get(), new AvoidRecursivityContext());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Superhero> productOptional = repository.findById(id);
        // apply ifPresentOrElse
        productOptional.ifPresentOrElse(superheroDb -> {
            repository.delete(superheroDb);
        },
        () -> {
            throw new SuperheroNotFoundException(id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuperheroDTO> findByName(@NotNull String name) {
        return superheroMapper.listEntityToDTO(repository.findByNameContaining(name).stream().collect(Collectors.toList()), new AvoidRecursivityContext());
    }
    @Override
    @Transactional(readOnly = true)
    public List<SuperheroDTO> findByNameQuery(@NotNull String name) {
        return superheroMapper.listEntityToDTO(repository.findByNameQuery(name).stream().collect(Collectors.toList()), new AvoidRecursivityContext());
    }

}
