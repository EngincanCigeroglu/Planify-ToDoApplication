package com.todoproject.note_database.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class MyNotes(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val date: Long,
    val note: String
)