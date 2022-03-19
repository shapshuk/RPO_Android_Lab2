package com.example.lab2.services

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface AuthService {
    val isLoggedIn: Boolean
    val user: User?
    fun signUp(email: String, password: String, completion: (success: Boolean) -> Unit )
    fun signIn(email: String, password: String, completion: (success: Boolean) -> Unit )
}

interface User {
    val id: String
    val name: String
    val email: String
}

data class DefaultUser (override val id: String, override val name: String, override val email: String) : User

class FirebaseAuthService (private val auth: FirebaseAuth = Firebase.auth) : AuthService {

    override val isLoggedIn: Boolean
        get() = auth.currentUser != null

    override val user: User?
        get() {
            val fUser = auth.currentUser
            if (fUser != null) {
                return DefaultUser(fUser.uid, fUser.displayName ?: "user",  fUser.email ?: "email")
            }
            return null
        }

    override fun signUp(email: String, password: String, completion: (success: Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task -> completion(task.isSuccessful) }
    }

    override fun signIn(email: String, password: String, completion: (success: Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task -> completion(task.isSuccessful) }
    }

}