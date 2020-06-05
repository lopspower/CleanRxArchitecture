package com.mikhaellopez.domain.exception

sealed class AppException(message: String) : RuntimeException(message)

/**
 * Exception used when it is impossible to get data due to a lack of connection
 */
object NoConnectedException : AppException("No connection")

/**
 * Exception used when persistence method return false on SingleUseCase
 */
object PersistenceException : AppException("Data persistence error occurred")