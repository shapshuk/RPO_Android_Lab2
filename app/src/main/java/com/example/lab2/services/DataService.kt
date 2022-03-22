package com.example.lab2.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lab2.models.Content
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface DataService {
    val entities: LiveData<List<Content>>
    fun getEntityById(id: String) : LiveData<Content>
}

class FirebaseDataService(private val firestore: FirebaseFirestore = Firebase.firestore) : DataService {

    private var _entities: MutableLiveData<List<Content>> = MutableLiveData()
    override val entities: LiveData<List<Content>> get() =  _entities

    init {
        getObserver()
    }

    private fun getObserver() {
        firestore.collection("entities").addSnapshotListener { value, _ ->
            val tempEntities : MutableList<Content> = mutableListOf()
            for (doc in value!!) {
                val entityItem = doc.toObject(Content::class.java)
                entityItem.id = doc.id
                tempEntities.add(entityItem)
            }
            _entities.value = tempEntities
        }
    }

    override fun getEntityById(id: String) : LiveData<Content> {
        return _entities
            .map { items ->
                items.filter { it.id.toString() == id }[0]
            }
    }
}