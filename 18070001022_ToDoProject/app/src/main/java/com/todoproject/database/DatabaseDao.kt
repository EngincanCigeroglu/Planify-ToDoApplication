package com.todoproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todoproject.database.models.MyData

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM my_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<MyData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(myData: MyData)

    @Update
    suspend fun updateData(myData: MyData)

    @Delete
    suspend fun deleteItem(myData: MyData)

    @Query("DELETE FROM my_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM my_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<MyData>>

    @Query("SELECT * FROM my_table ORDER BY date DESC")
    fun sortByDateDesc(): LiveData<List<MyData>>

    @Query("SELECT * FROM my_table  ORDER BY date ASC")
    fun sortByDateAsc(): LiveData<List<MyData>>

    @Query("SELECT * FROM my_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<MyData>>

    @Query("SELECT * FROM my_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<MyData>>


}
