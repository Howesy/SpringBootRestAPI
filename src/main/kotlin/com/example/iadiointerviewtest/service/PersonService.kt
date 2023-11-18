package com.example.iadiointerviewtest.service

import com.example.iadiointerviewtest.entity.Person
import com.example.iadiointerviewtest.exception.*
import com.example.iadiointerviewtest.repository.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest

@Service
class PersonService(private val personRepository: PersonRepository) {

    /**
     * Allocate a person to the database.
     * @param person The person entity to add to the database.
     * @return Person The person added to the database.
     */

    fun createPerson(person: Person): Person {
        if (personRepository.findUniqueUsername(person.username) != null)
            throw PersonAlreadyExists("ERROR: Unable to create person, a person already exists with that username!")

        try { person.age.toInt() } catch(exception: NumberFormatException) {
            throw IllegalAgeConversion("ERROR: Please specify a number for your age.")
        }

        return personRepository.save(person)
    }

    /**
     * Retrieve all persons from the database by page number and amount.
     * @param pageNumber The page you to access.
     * @param pageSize The amount of records to return.
     * @return Page<Person> page of person records.
     */

    fun retrieveAllPersons(pageNumber: Int, pageSize: Int): Page<Person> {
        val pageable = PageRequest.of(pageNumber, pageSize)
        return personRepository.findAll(pageable)
    }

    /**
     * Retrieve all persons filtered by their first name.
     * @param personName The first name of the person to filter by.
     * @return List<Person> list of person records.
     */

    fun retrieveAllPersonsByName(personName: String):
            List<Person> = personRepository.findByPersonName(personName)

    /**
     * Retrieve all persons filtered by their age.
     * @param personAge The age of the person to filter by.
     * @return List<Person> list of person records.
     */

    fun retrieveAllPersonsByAge(personAge: String):
            List<Person> = personRepository.findByPersonAge(personAge)

    /**
     * Retrieve all persons filtered by identifiers (name and age)
     * @param personName The first name of the person to filter by.
     * @param personAge The age of the person to filter by.
     * @return List<Person> list of person records.
     */

    fun retrieveAllPersonsByIdentifiers(personName: String?, personAge: String?):
            List<Person> = personRepository.findPersonsByIdentifiers(personName, personAge)

    /**
     * Retrieve person by ID.
     * @param personID The ID of the person to retrieve.
     * @return Person The person entity.
     */

    fun retrievePerson(personID: Long):
            Person = personRepository.findById(personID)
            .orElseThrow{ PersonNotFound("ERROR: Unable to retrieve person matching that ID, they do not exist.") }

    /**
     * Update a person by ID.
     * @param personID The ID of the person to update.
     * @param person The data to update the person with.
     * @return Person The updated person entity.
     */

    fun updatePerson(personID: Long, person: Person): Person {
        if (personRepository.existsById(personID)) {

            val newPerson = Person(
                    id = person.id,
                    firstName = person.firstName,
                    lastName = person.lastName,
                    email = person.email,
                    phone = person.phone,
                    dateOfBirth = person.dateOfBirth,
                    age = person.age,
                    username = person.username,
                    password = person.password
            )

            return personRepository.save(newPerson)
        } else throw PersonNotFound("ERROR: Unable to update person matching that ID, they do not exist.")
    }

    /**
     * Delete a person by ID.
     * @param personID The ID of the person to delete.
     * @return N/A
     */

    fun deletePerson(personID: Long): Unit {
        if (personRepository.existsById(personID)) {
            return personRepository.deleteById(personID)
        } else throw PersonNotFound("ERROR: Unable to delete person matching that ID, they do not exist.")
    }

}