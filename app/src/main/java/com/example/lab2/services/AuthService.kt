package com.example.lab2.services

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface AuthService {
    val isLoggedIn: LiveData<Boolean>
    val user: LiveData<User?>
    fun signUp(email: String, password: String)
    fun signIn(email: String, password: String)
    fun signOut()
}

interface User {
    val id: String
    val name: String
    val email: String
}

data class DefaultUser (override val id: String, override val name: String, override val email: String) : User

class FirebaseAuthService (private val auth: FirebaseAuth = Firebase.auth) : AuthService {

    private val _isLoggedIn : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _user: MutableLiveData<User?> = MutableLiveData(null)

    init {
        auth.addAuthStateListener {
            _isLoggedIn.postValue(it.currentUser != null)
            _user.postValue(
                if (it.currentUser != null)
                    DefaultUser(it.currentUser!!.uid, it.currentUser!!.displayName ?: "user",  it.currentUser!!.email ?: "email")
                else
                    null
            )
        }
    }

    override val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    override val user: LiveData<User?>
        get() = _user

    override fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    override fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override fun signOut() {
        auth.signOut()
    }
}