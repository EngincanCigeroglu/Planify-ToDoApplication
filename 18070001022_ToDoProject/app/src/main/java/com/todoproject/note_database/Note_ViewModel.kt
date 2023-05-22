package com.todoproject.note_database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todoproject.note_database.models.MyNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Note_ViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: Note_databasedao = Note_database.getDatabase(application).Note_databasedao()
    private val repository: Note_repository = Note_repository(noteDao)

    val getAllData: LiveData<List<MyNotes>> = repository.getAllData

    fun insertNote(note: MyNotes) {
        viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
        }
    }

   fun updateNote(note: MyNotes) {
       viewModelScope.launch(Dispatchers.IO) {
           repository.updateNote(note)
       }
    }

    fun searchDatabase(searchQuery: Long): LiveData<MyNotes> {
        return repository.searchDatabase(searchQuery)
    }
}