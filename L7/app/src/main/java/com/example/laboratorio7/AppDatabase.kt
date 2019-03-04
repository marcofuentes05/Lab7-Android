package com.example.laboratorio7

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contacto::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun contactoDAO(): ContactoDAO

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "contactos"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }


    class PopulateDbAsyncTask(db: AppDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val contactoDao = db?.contactoDAO()

        override fun doInBackground(vararg p0: Unit?) {
            contactoDao?.insertarContacto(Contacto("Juanito", "12345678", "juanito@gmail.com",1, ""))
        }
    }
}