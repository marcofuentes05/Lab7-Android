package com.example.ejemploroom
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var repository: NoteRepository =
        NoteRepository(application)
    private var allNotes: LiveData<List<Note>> = repository.getAllNotes()
    fun insert (note:Note){
        repository.insert(note)
    }
    fun update(note:Note){
        repository.update(note)
    }
    fun delete(note: Note) {
        repository.delete(note)
    }
    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}