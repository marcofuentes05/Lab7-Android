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

    fun update(c: Contacto){
        val updateCAsyncTask = UpdateCAsyncTask(contactoDAO).execute(c)
    }

    fun deleteAll(){
        val deleteAllCAsyncTask = DeleteAllCAsyncTask(contactoDAO).execute()
    }

    fun delete(c: Contacto){
        val deleteCAsyncTask = DeleteCAsyncTask(contactoDAO).execute(c)
    }

    fun getAllC() : LiveData<List<Contacto>>{
            return todosContactos
    }

    companion object {
        private class InsertCAsyncTask(cDAO: ContactoDAO) : AsyncTask<Contacto, Unit, Unit>() {
            val contactoDAO = cDAO

            override fun doInBackground(vararg p0: Contacto?) {
                contactoDAO.insertarContacto(p0[0]!!)
            }
        }

        private class UpdateCAsyncTask(cDAO: ContactoDAO) : AsyncTask<Contacto, Unit, Unit>() {
            val contactoDAO = cDAO

            override fun doInBackground(vararg p0: Contacto?) {
                contactoDAO.actualizarContacto(p0[0]!!)
            }
        }

        private class DeleteAllCAsyncTask(cDAO: ContactoDAO) : AsyncTask<Contacto, Unit, Unit>() {
            val contactoDAO = cDAO
            override fun doInBackground(vararg p0: Contacto?) {
                contactoDAO.eliminarTodosContactos()
            }
        }

        private class DeleteCAsyncTask(cDAO: ContactoDAO) : AsyncTask<Contacto, Unit, Unit>() {
            val contactoDAO = cDAO

            override fun doInBackground(vararg p0: Contacto?) {
                contactoDAO.eliminarContacto(p0[0]!!)
            }
        }
    }
}