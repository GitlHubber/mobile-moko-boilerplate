package org.example.library.repositoryImpl

import org.example.library.feature.auth.di.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    override suspend fun auth(login: String, password: String) {
        if (login == "hellompp" && password == "kotlin") {
            println("login successfull")
        } else {
            throw Exception("Неверный логин/пароль")
        }
    }
}