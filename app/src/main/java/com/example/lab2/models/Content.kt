package com.example.lab2.models

data class Content(var id: String, val imageUrl: String, val name: String, val videoUrl: String) {

    constructor():this("","", "", "")
}