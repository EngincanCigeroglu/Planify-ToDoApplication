package com.todoproject.database.models
import android.os.Parcelable
import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "my_table")
@Parcelize
data class MyData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: Long,
    ): Parcelable