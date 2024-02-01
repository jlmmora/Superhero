package com.plexus.w2m.controllers;

import com.plexus.w2m.annotation.LogExecutionTime;
import com.plexus.w2m.dto.SuperheroDTO;
import com.plexus.w2m.mapper.AvoidRecursivityContext;
import com.plexus.w2m.mapper.SuperheroMapper;
import com.plexus.w2m.services.SuperheroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/superhero")
public class SuperheroController {

    @Autowired
    private SuperheroService service;

    @Autowired
    private SuperheroMapper mapper;

    @LogExecutionTime
    @GetMapping("/{id}")
    @Operation(summary = "findById. Obtener el Superheroe que cumplen con el valor del id del parametro de entrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SuperheroDTO superheroDTO = service.findById(id);
        if (superheroDTO != null) {
            return ResponseEntity.ok(superheroDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Cacheable("superhero")
    @LogExecutionTime
    @GetMapping("/findAll")
    @Operation(summary = "findAll. Obtener todos los Superheroes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Superheroe", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    public ResponseEntity<?> findAll() {
        List<SuperheroDTO> superheroDTO = service.findAll();
        if (superheroDTO != null) {
            return ResponseEntity.ok(superheroDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @LogExecutionTime
    @GetMapping("/findByName")
    @Operation(summary = "findByName. Obtener el Superheroe que contiene, en su nombre, el valor del parámetro introducido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    @Cacheable("superhero")
    public ResponseEntity<List<SuperheroDTO>> findByName(@RequestParam(defaultValue = "") String name) {
        final List<SuperheroDTO> superheroes = service.findByName(name);
        return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }

    @LogExecutionTime
    @GetMapping("/findByNameQuery")
    @Operation(summary = "findByNameQuery. Obtener el Superheroe que contiene, en su nombre, el valor del parámetro introducido utilizando la anotación @Query.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    @Cacheable("superhero")
    public ResponseEntity<List<SuperheroDTO>> findByNameQuery(@RequestParam(defaultValue = "") String name) {
        final List<SuperheroDTO> superheroes = service.findByNameQuery(name);
        return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }

    @LogExecutionTime
    @PostMapping
    @Operation(summary = "create. Creación de un Superheroe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe creado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    public ResponseEntity<?> create(@Valid @RequestBody SuperheroDTO superheroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(mapper.dtoToEntity(superheroDTO, new AvoidRecursivityContext())));
    }

    @LogExecutionTime
    @PutMapping
    @Operation(summary = "update. Actualización de un Superheroe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe actualizado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    public ResponseEntity<?> update(@Valid @RequestBody SuperheroDTO superheroDTO) {
        SuperheroDTO superheroBD = service.update(mapper.dtoToEntity(superheroDTO, new AvoidRecursivityContext()));
        if (superheroBD != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(superheroBD);
        }
        return ResponseEntity.notFound().build();
    }

    @LogExecutionTime
    @DeleteMapping("/{id}")
    @Operation(summary = "delete. Borrado de un Superheroe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroe borrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroDTO.class))}),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Superheroe no encontrado", content = @Content)})
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
