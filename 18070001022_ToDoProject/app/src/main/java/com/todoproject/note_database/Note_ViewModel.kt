package com.todoproject.note_database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todoproject.note_database.models.MyNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Note_ViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository: Note_repository

    init {
        val noteDao = Note_database.getDatabase(application).Note_databasedao()
        noteRepository = Note_repository(noteDao)
    }

    val getAllData: LiveData<List<MyNotes>> = noteRepository.getAllData

    fun insertNote(note: MyNotes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: MyNotes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
        }
    }

    fun searchDatabase(searchQuery: Long): LiveData<MyNotes> {
        return noteRepository.searchDatabase(searchQuery)
    }
}