package com.example.iadiointerviewtest.dto

/**
 * DTO class for removing username and password for guest API
 * @property id ID of the person entity
 * @property firstName First name of the person entity
 * @property lastName Last name of the person entity
 * @property email Email of the person entity
 * @property phone Phone number of the person entity
 * @property dateOfBirth Date of birth of the person entity
 * @property age Age of the person entity
 */

data class CleanedPerson(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String,
    val age: String
)