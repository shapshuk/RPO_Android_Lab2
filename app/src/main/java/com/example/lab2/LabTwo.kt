package com.example.lab2

import android.app.Application
import com.google.firebase.FirebaseApp

class LabTwo : Application() {
    override fun onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}