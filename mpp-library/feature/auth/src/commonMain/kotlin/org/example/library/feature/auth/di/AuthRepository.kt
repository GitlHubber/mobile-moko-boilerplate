package org.example.library.feature.auth.di

interface AuthRepository {
    suspend fun auth(login: String, password: String)
}