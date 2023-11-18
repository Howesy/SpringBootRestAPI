package com.example.iadiointerviewtest.exception

/**
 * Constructor class for Exception error message.
 * @param statusCode Status code of the exception.
 * @param message Message of the exception.
 */

class APIErrorMessage(
    val statusCode: Int? = null,
    val message: String? = null
)