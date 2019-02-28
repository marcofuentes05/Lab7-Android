package com.example.laboratorio7

import android.arch.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactoDAO {
    @Query("SELECT * FROM tabla_contactos ORDER BY prioridad DESC")
    fun getContactos(): LiveData<List<Contacto>>

    @Insert
    fun insertarContacto(c: Contacto)

    @Update
    fun actualizarContacto(c: Contacto)

    @Delete
    fun eliminarContactos(vararg contactos: Contacto)

    @Delete
    fun eliminarContacto(contacto: Contacto)
}