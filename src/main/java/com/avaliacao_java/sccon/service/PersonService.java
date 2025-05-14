package com.avaliacao_java.sccon.service;

import com.avaliacao_java.sccon.exception.ResourceNotFoundException;
import com.avaliacao_java.sccon.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final Map<Long, Person> persons = new HashMap<>();
    private static final BigDecimal MINIMUM_SALARY = new BigDecimal("1302.00");
    private static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");

    @PostConstruct
    public void init() {
        persons.put(1L, new Person(1L, "João Silva", LocalDate.of(1990, 5, 15), LocalDate.of(2020, 1, 10)));
        persons.put(2L, new Person(2L, "Maria Oliveira", LocalDate.of(1985, 8, 22), LocalDate.of(2018, 3, 5)));
        persons.put(3L, new Person(3L, "Carlos Souza", LocalDate.of(1995, 2, 10), LocalDate.of(2022, 7, 20)));
    }

    public List<Person> getAllPersons() {
        return persons.values().stream()
                .sorted(Comparator.comparing(Person::getName))
                .collect(Collectors.toList());
    }

    public Person addPerson(Person person) {
        Long id = person.getId();
        if (id == null) {
            id = persons.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
            person.setId(id);
        } else if (persons.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Person with ID " + id + " already exists");
        }
        persons.put(id, person);
        return person;
    }

    public void deletePerson(Long id) {
        if (!persons.containsKey(id)) {
            throw new ResourceNotFoundException("Person with ID " + id + " not found");
        }
        persons.remove(id);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        if (!persons.containsKey(id)) {
            throw new ResourceNotFoundException("Person with ID " + id + " not found");
        }
        updatedPerson.setId(id);
        persons.put(id, updatedPerson);
        return updatedPerson;
    }

    public Person patchPerson(Long id, Person updatedFields) {
        Person existingPerson = persons.get(id);
        if (existingPerson == null) {
            throw new ResourceNotFoundException("Person with ID " + id + " not found");
        }
        if (updatedFields.getName() != null) {
            existingPerson.setName(updatedFields.getName());
        }
        if (updatedFields.getBirthDate() != null) {
            existingPerson.setBirthDate(updatedFields.getBirthDate());
        }
        if (updatedFields.getAdmissionDate() != null) {
            existingPerson.setAdmissionDate(updatedFields.getAdmissionDate());
        }
        return existingPerson;
    }

    public Person getPersonById(Long id) {
        Person person = persons.get(id);
        if (person == null) {
            throw new ResourceNotFoundException("Person with ID " + id + " not found");
        }
        return person;
    }

    public long getAge(Long id, String output) {
        Person person = getPersonById(id);
        LocalDate now = LocalDate.now();
        switch (output.toLowerCase()) {
            case "days":
                return ChronoUnit.DAYS.between(person.getBirthDate(), now);
            case "months":
                return ChronoUnit.MONTHS.between(person.getBirthDate(), now);
            case "years":
                return ChronoUnit.YEARS.between(person.getBirthDate(), now);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid output format: " + output);
        }
    }

    public BigDecimal getSalary(Long id, String output) {
        Person person = getPersonById(id);
        long years = ChronoUnit.YEARS.between(person.getAdmissionDate(), LocalDate.now());
        BigDecimal salary = INITIAL_SALARY;
        for (int i = 0; i < years; i++) {
            salary = salary.multiply(new BigDecimal("1.18")).add(new BigDecimal("500"));
        }
        salary = salary.setScale(2, RoundingMode.CEILING);
        switch (output.toLowerCase()) {
            case "full":
                return salary;
            case "min":
                return salary.divide(MINIMUM_SALARY, 2, RoundingMode.CEILING);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid output format: " + output);
        }
    }
}
