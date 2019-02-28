package com.example.laboratorio7

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import androidx.room.Room

class Repositorio(application: Application) {
    private var contactoDAO : ContactoDAO

    private var todosContactos : LiveData<List<Contacto>>

    init {
        val baseDatos : AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        contactoDAO = baseDatos.contactoDAO()
        todosContactos = contactoDAO.getContactos()
    }

    fun insert(c: Contacto){
        val insertCAsyncTask = InsertCAsyncTask(contactoDAO).execute(c)
    }




    companion object {
        private class InsertCAsyncTask(noteDao: ContactoDAO) : AsyncTask<Contacto, Unit, Unit>() {
            val noteDao = noteDao

            override fun doInBackground(vararg p0: Contacto?) {
                noteDao.insertarContacto(p0[0]!!)
            }
        }
    }
}