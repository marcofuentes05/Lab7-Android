package com.example.laboratorio7

import android.app.Application
import android.arch.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel

class ContactoViewModel(application : Application) : AndroidViewModel (application) {
    private var repository = Repositorio(application)
    private var todosC : LiveData <List<Contacto>> = repository.getAllC()

    fun insert(c: Contacto){
        repository.insert(c)
    }

    fun update(c: Contacto){
        repository.update(c)
    }

    fun delete(c : Contacto){
        repository.delete(c)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun getAllC(): LiveData<List<Contacto>> {
        return todosC
    }
}