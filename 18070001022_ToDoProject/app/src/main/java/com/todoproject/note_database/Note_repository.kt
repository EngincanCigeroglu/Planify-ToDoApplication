package com.todoproject.note_database

import androidx.lifecycle.LiveData
import com.todoproject.note_database.models.MyNotes


class Note_repository (private val noteDao: Note_databasedao) {

    val getAllData: LiveData<List<MyNotes>> = noteDao.getAllData()

    fun insertNote(note: MyNotes){
        noteDao.insertNote(note)
    }

    fun updateNote(note: MyNotes) {
        noteDao.updateNote(note)
    }


    fun searchDatabase(searchQuery: Long): LiveData<MyNotes> {
        return noteDao.searchDatabase(searchQuery)
    }
}