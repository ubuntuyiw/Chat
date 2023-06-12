package com.ubuntuyouiwe.chat.presentation.login

sealed class LoginEvent(val email: String, val password: String) {
    class SignUp(email: String, password: String) : LoginEvent(email, password)

    class LogIn(email: String, password: String) : LoginEvent(email, password)

}

