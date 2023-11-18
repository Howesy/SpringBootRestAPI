package com.example.iadiointerviewtest.entity

import com.example.iadiointerviewtest.dto.CleanedPerson
import jakarta.persistence.*

/**
 * Entity class for my person
 * @property id ID of the person entity
 * @property firstName First name of the person entity
 * @property lastName Last name of the person entity
 * @property email Email of the person entity
 * @property phone Phone number of the person entity
 * @property dateOfBirth Date of birth of the person entity
 * @property age Age of the person entity
 * @property username Username of the person entity
 * @property password Password of the person entity
 *
 */

@Entity
@Table(name = "person")
data class Person (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        @Column(name = "name", nullable = false)
        val firstName: String,
        @Column(name = "surname", nullable = false)
        val lastName: String,
        @Column(name = "email", nullable = false)
        val email: String,
        @Column(name = "phone", nullable = false)
        val phone: String,
        @Column(name = "dateOfBirth", nullable = false)
        val dateOfBirth: String,
        @Column(name = "age", nullable = false)
        val age: String,
        @Column(name = "username", unique = true, nullable = false)
        val username: String,
        @Column(name = "password", nullable = false)
        val password: String
)

/**
 * Convert Person entity to CleanedPerson entity
 */

fun Person.clean() = CleanedPerson(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        dateOfBirth = dateOfBirth,
        age = age
)