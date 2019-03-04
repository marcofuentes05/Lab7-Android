package com.example.laboratorio7

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactoDAO {
    @Query("SELECT * FROM tabla_contactos ORDER BY prioridad DESC")
    fun getContactos(): LiveData<List<Contacto>>

    @Insert
    fun insertarContacto(c: Contacto)

    @Update
    fun actualizarContacto(c: Contacto)

    @Query ("DELETE FROM tabla_contactos")
    fun eliminarTodosContactos()

    @Delete
    fun eliminarContacto(contacto: Contacto)
}