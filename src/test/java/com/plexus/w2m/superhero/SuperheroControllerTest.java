package com.plexus.w2m.superhero;

import com.plexus.w2m.controllers.SuperheroController;
import com.plexus.w2m.dto.SuperheroDTO;
import com.plexus.w2m.entities.Superhero;
import com.plexus.w2m.exception.SuperheroNotFoundException;
import com.plexus.w2m.mapper.AvoidRecursivityContext;
import com.plexus.w2m.mapper.SuperheroMapper;
import com.plexus.w2m.repository.SuperheroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SuperheroControllerTest {

    @Autowired
    private SuperheroRepository  repository;
    @Autowired
    private SuperheroController controller;

    private Superhero spiderman;
    private Superhero hulk;
    private Superhero superman;
    private SuperheroDTO superheroDTO;

    @Autowired
    private SuperheroMapper mapper;

    @BeforeEach
    private void setup() {
        repository.deleteAll();


        hulk = repository.save(Superhero.builder()
                .name("Hulk")
                .realName("Bruce Banner")
                .power(100.0)
                .abilities("Súper fuerza, súper velocidad, súper resistencia, súper salto, Invulnerabilidad, regeneración, longevidad")
                .build());

        superman = repository.save(Superhero.builder()
                .name("Superman")
                .realName("Clark Kent")
                .power(100.0)
                .abilities("Súper fuerza, súper velocidad, súper resistencia, súper salto, Invulnerabilidad, regeneración, Longevidad, súper audición, poderes oculares")
                .build());

        spiderman = repository.save(Superhero.builder()
                .name("Spiderman")
                .realName("Peter Parker")
                .power(90.0)
                .abilities("Fuerza, velocidad, agilidad, sentidos, reflejos, coordinación, equilibrio y resistencia sobrehumanos")
                .build());

        Superhero blackWidow = repository.save(Superhero.builder()
                .name("Black Widow")
                .realName("Natasha Romanova")
                .power(85.0)
                .abilities("Envejecimiento lento, sistema inmunológico mejorado, tiradora experta, dominio de armas, experta táctica, combatiente cuerpo a cuerpo ")
                .build());

    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de creación de un superheroe")
    public void create() {
        // WHEN
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        Superhero manuel = Superhero.builder()
                .name("Manolito el fuerte")
                .realName("Manuel Diaz")
                .power(100.0)
                .abilities("Súper fuerza, súper velocidad, súper resistencia, súper salto, Invulnerabilidad, regeneración, longevidad")
                .build();

        ResponseEntity<?> entity = this.controller.create(mapper.entityToDTO(manuel, new AvoidRecursivityContext()),result);

        // THEN
        assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));

        SuperheroDTO superhero = (SuperheroDTO) entity.getBody();
        assertThat(superhero, notNullValue());
        assertThat(superhero.getName(), is("Manolito el fuerte"));
        assertThat(superhero.getRealName(), is("Manuel Diaz"));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de actualización de un superheroe")
    public void update() {
        // WHEN
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        spiderman.setPower(95.0);
        ResponseEntity<?> entity = this.controller.update(mapper.entityToDTO(spiderman, new AvoidRecursivityContext()),result);

        // THEN
        assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));

        SuperheroDTO superhero = (SuperheroDTO) entity.getBody();
        assertThat(superhero, notNullValue());
        assertThat(superhero.getPower(), is(95.0));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de borrado de un superheroe")
    public void delete() {
        this.controller.delete(hulk.getId());
        assertThrows(SuperheroNotFoundException.class, () -> controller.findById(hulk.getId()));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de borrado de un superheroe lanzando una excepción si no existe el superheroe que se desea borrar")
    public void errorDelete() {

        assertThrows(SuperheroNotFoundException.class, () -> this.controller.delete(100L));
    }

    @Test
    @DisplayName("test que evalúa la funcionalidad de búsqueda de todos superheroe")
    public void findAll() {
        ResponseEntity<?> entity = this.controller.findAll();

        // THEN
        List<SuperheroDTO> list = (List<SuperheroDTO>) entity.getBody();
        assertThat(list, notNullValue());
        assertThat(list.size(), is(4));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de búsqueda por id superheroe")
    public void findById() {
        ResponseEntity<?> entity = this.controller.findById(hulk.getId());

        SuperheroDTO dto = (SuperheroDTO) entity.getBody();
        assertThat(dto, notNullValue());
        assertThat(dto.getName(), is(hulk.getName()));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de búsqueda de un superheroe por nombre")
    public void findByName() {
        ResponseEntity<?> entity = this.controller.findByName("man");

        List<SuperheroDTO> list = (List<SuperheroDTO>) entity.getBody();
        assertThat(list, notNullValue());
        assertThat(list.size(), is(2));
    }
    @Test
    @DisplayName("test que evalúa la funcionalidad de búsqueda de un superheroe por nombre utilizando la anotación @Query")
    public void findByNameQuery() {
        ResponseEntity<?> entity = this.controller.findByNameQuery("man");

        List<SuperheroDTO> list = (List<SuperheroDTO>) entity.getBody();
        assertThat(list, notNullValue());
        assertThat(list.size(), is(2));
    }
}