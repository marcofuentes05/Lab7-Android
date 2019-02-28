package com.example.ejemploroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "note_table")
data class Note (
    val title : String,
    var description: String,
    var priority: Int
){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}