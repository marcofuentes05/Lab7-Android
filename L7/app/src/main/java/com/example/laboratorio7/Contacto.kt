package com.example.laboratorio7

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "tabla_contactos")
data class Contacto (
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "telefono") var telefono: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "prioridad") var prioridad: Int,
    @ColumnInfo(name = "foto") var foto: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}