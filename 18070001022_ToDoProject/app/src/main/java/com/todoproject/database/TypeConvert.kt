package com.todoproject.database

import androidx.room.TypeConverter
import com.todoproject.database.models.Priority
import java.util.*

class TypeConvert {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}