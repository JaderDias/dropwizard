package com.example.helloworld.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.helloworld.core.Person;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
class PersonDAOTest {

    private PersonDAO personDAO;

    @BeforeEach
    void setUp() throws Exception {
        personDAO = new PersonDAO();
    }

    @Test
    void createPerson() {
        final Person jeff = personDAO.create(new Person(
                1,
                "Jeff",
                "The plumber",
                1995));
        assertThat(jeff.getId()).isEqualTo(1);
        assertThat(jeff.getFullName()).isEqualTo("Jeff");
        assertThat(jeff.getJobTitle()).isEqualTo("The plumber");
        assertThat(jeff.getYearBorn()).isEqualTo(1995);
        assertThat(personDAO.findById(jeff.getId())).isEqualTo(Optional.of(jeff));
    }

    @Test
    void findAll() {
        personDAO.create(new Person(1, "Jeff", "The plumber", 1975));
        personDAO.create(new Person(2, "Jim", "The cook", 1985));
        personDAO.create(new Person(3, "Randy", "The watchman", 1995));

        final List<Person> persons = personDAO.findAll();
        assertThat(persons).extracting("fullName").containsOnly("Jeff", "Jim", "Randy");
        assertThat(persons).extracting("jobTitle").containsOnly("The plumber", "The cook", "The watchman");
        assertThat(persons).extracting("yearBorn").containsOnly(1975, 1985, 1995);
    }
}
