package com.example.iadiointerviewtest.repository

import com.example.iadiointerviewtest.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    /**
     * Repository declaration for finding persons by name using JPQL.
     * @param firstName The first name of the persons to find.
     * @return List<Person> List of persons whose firstName is similar to specified name.
     */

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE %:name%")
    fun findByPersonName(@Param("name") firstName: String): List<Person>

    /**
     * Repository declaration for finding persons by age using JPQL.
     * @param age The age of the persons to find.
     * @return List<Person> List of persons whose age is as specified.
     */

    @Query("SELECT p FROM Person p WHERE p.age = :age")
    fun findByPersonAge(@Param("age") age: String): List<Person>

    /**
     * Repository declaration for finding persons by identifiers: firstName and age
     * @param firstName The first name of the persons to find.
     * @param age The age of the persons to find.
     * @return List<Person> List of persons whose identifiers meet the specified parameters.
     */

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE %:name% AND p.age = :age")
    fun findPersonsByIdentifiers(@Param("name") firstName: String?, @Param("age") age: String?): List<Person>

    /**
     * Repository declaration for finding a person by their unique username.
     * @param username The username of the person to find.
     * @return Person The person entity regarding the unique username.
     */

    @Query("SELECT p FROM Person p WHERE p.username = :username")
    fun findUniqueUsername(@Param("username") username: String): Person?
}