package com.todoproject.note_database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todoproject.database.models.MyData
import com.todoproject.note_database.models.MyNotes

@Dao
interface Note_databasedao {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllData(): LiveData<List<MyNotes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(MyNotes: MyNotes)

    @Update
    fun updateNote(MyNotes: MyNotes)

    @Query("SELECT * FROM notes WHERE date LIKE :searchQuery")
    fun searchDatabase(searchQuery: Long): LiveData<MyNotes>
}