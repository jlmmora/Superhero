package com.plexus.w2m.repository;

import com.plexus.w2m.entities.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long>{
    List<Superhero> findByNameContaining(String name);

    @Query("select u from Superhero u where lower(u.name) like lower(concat('%', :name, '%'))")
    List<Superhero> findByNameQuery(@Param("name") String name);
}
