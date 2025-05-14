package com.avaliacao_java.sccon.controller;

import com.avaliacao_java.sccon.model.Person;
import com.avaliacao_java.sccon.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Person API", description = "Endpoints for managing persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    @Operation(summary = "List all persons", description = "Returns a list of all persons sorted alphabetically by name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of persons retrieved successfully", content = @Content(schema = @Schema(implementation = Person.class)))
    })
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    @Operation(summary = "Create a new person", description = "Adds a new person to the in-memory map. If ID is not provided, it will be auto-generated.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person created successfully", content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(responseCode = "409", description = "Conflict: Person with provided ID already exists")
    })
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person", description = "Removes a person by ID from the in-memory map")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Void> deletePerson(@PathVariable @Parameter(description = "ID of the person to delete") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person", description = "Updates all fields of a person by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person updated successfully", content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public Person updatePerson(@PathVariable @Parameter(description = "ID of the person to update") Long id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a person", description = "Updates specified fields of a person by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person updated successfully", content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public Person patchPerson(@PathVariable @Parameter(description = "ID of the person to update") Long id, @RequestBody Person person) {
        return personService.patchPerson(id, person);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID", description = "Retrieves a person by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Person retrieved successfully", content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public Person getPersonById(@PathVariable @Parameter(description = "ID of the person to retrieve") Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/{id}/age")
    @Operation(summary = "Get person's age", description = "Calculates the age of a person in days, months, or years")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Age calculated successfully", content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "404", description = "Person not found"),
        @ApiResponse(responseCode = "400", description = "Invalid output format")
    })
    public long getAge(@PathVariable @Parameter(description = "ID of the person") Long id, @RequestParam @Parameter(description = "Output format: days, months, or years") String output) {
        return personService.getAge(id, output);
    }

    @GetMapping("/{id}/salary")
    @Operation(summary = "Get person's salary", description = "Calculates the salary in full (R$) or in minimum wages")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Salary calculated successfully", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
        @ApiResponse(responseCode = "404", description = "Person not found"),
        @ApiResponse(responseCode = "400", description = "Invalid output format")
    })
    public BigDecimal getSalary(@PathVariable @Parameter(description = "ID of the person") Long id,
                                @RequestParam @Parameter(description = "Output format: full or min") String output) {
        return personService.getSalary(id, output);
    }
}
