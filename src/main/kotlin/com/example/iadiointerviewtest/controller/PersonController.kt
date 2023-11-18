package com.example.iadiointerviewtest.controller

import com.example.iadiointerviewtest.dto.CleanedPerson
import com.example.iadiointerviewtest.entity.Person
import com.example.iadiointerviewtest.entity.clean
import com.example.iadiointerviewtest.service.PersonService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController(private val personService: PersonService) {

    /**
     * API Endpoint: /persons, HTTP Method: GET,
     * Retrieve all persons contained within the database.
     * Example: persons?page=0&amount=10
     * @param page The page to access involving pagination
     * @param amount The amount of records to show from the specified page
     * @return Page<Person> Returns paginated results of persons
     */

    @GetMapping("/persons")
    fun retrieveAllPersons(@RequestParam page: Int, @RequestParam amount: Int): Page<Person> {
        return personService.retrieveAllPersons(page, amount)
    }

    /**
     * API Endpoint: /searchPersons, HTTP Method: GET,
     * Retrieve all persons filtered by name (partial) and/or age without the username and password.
     * This API Endpoint is available to the GUEST role.
     * Example: searchPersons?name=Charlie&age=22
     * @param name Name of the person you are searching for.
     * @param age Age of the person you are searching for.
     * @return List<CleanedPerson> List of "cleaned" persons, (no username and password)
     */

    @GetMapping("/searchPersons")
    fun searchPersons(@RequestParam(required = false) name: String?, @RequestParam(required = false) age: String?): List<CleanedPerson> {

        //Neither parameter supplied.
        if (name == null && age == null)
            return listOf<CleanedPerson>()

        //Either or parameter supplied.
        if (name != null && age == null) {
            val allPersons = personService.retrieveAllPersonsByName(name)
            return cleanPersonList(allPersons)
        } else if (age != null && name == null) {
            val allPersons = personService.retrieveAllPersonsByAge(age)
            return cleanPersonList(allPersons)
        }

        //Both parameters supplied.
        val persons = personService.retrieveAllPersonsByIdentifiers(name, age)
        return cleanPersonList(persons)
    }

    /**
     * API Endpoint: /persons/{id}, HTTP Method: GET,
     * Retrieve a person from the database by ID.
     * Example: /persons/1
     * @param id The Person ID to search for.
     * @return Person The Person entity.
     */

    @GetMapping("/persons/{id}")
    fun retrievePerson(@PathVariable("id") id: Long):
            Person = personService.retrievePerson(id)

    /**
     * API Endpoint: /persons, HTTP Method: POST,
     * Add a person to the database.
     * Example: /persons, (provide raw JSON data for Person properties)
     * @param requestBody The raw JSON data
     * @return Person The created Person
     */

    @PostMapping("/persons")
    fun createPerson(@RequestBody requestBody: Person):
            Person = personService.createPerson(requestBody)

    /**
     * API Endpoint: /persons/{id}, HTTP Method: PUT,
     * Update a person in the database.
     * Example: /persons/1, (provide raw JSON data for Person properties)
     * @param personID The person id of the person to update
     * @param requestBody The raw JSON data
     * @return Person The updated person
     */

    @PutMapping("/persons/{id}")
    fun updatePerson(@PathVariable("id") personID: Long, @RequestBody requestBody: Person):
            Person = personService.updatePerson(personID, requestBody)

    /**
     * API Endpoint: /persons/{id}, HTTP Method: DELETE,
     * Delete a person in the database.
     * Example: /persons/1
     * @param personID THe person id to delete
     * @return Unit Returns nothing.
     */

    @DeleteMapping("/persons/{id}")
    fun deletePerson(@PathVariable("id") personID: Long):
            Unit = personService.deletePerson(personID)
}

/**
 * Function for converting Person entities into CleanedPerson DTOs to hide
 * the "username" and "password" property from GUEST view.
 *
 * @param personList List of persons to covert.
 * @return List<CleanedPerson> List of cleaned persons.
 */

fun cleanPersonList(personList: List<Person>): List<CleanedPerson> {
    val cleanedPersons = mutableListOf<CleanedPerson>()
    personList.forEach { person -> cleanedPersons.add(person.clean()) }
    return cleanedPersons
}