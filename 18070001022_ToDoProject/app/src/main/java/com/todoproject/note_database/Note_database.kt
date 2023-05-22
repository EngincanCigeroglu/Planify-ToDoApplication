package com.todoproject.note_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todoproject.note_database.models.MyNotes

@Database(entities = [MyNotes::class], version = 1, exportSchema = false)
abstract class Note_database : RoomDatabase() {
    abstract fun Note_databasedao(): Note_databasedao

    companion object {
        private var instance: Note_database? = null

        fun getDatabase(context: Context): Note_database {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    Note_database::class.java,
                    "notes.database"
                ).build()
                instance = database
                database
            }
        }
    }
}
