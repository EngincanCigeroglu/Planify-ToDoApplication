package com.todoproject.database

import androidx.lifecycle.LiveData
import com.todoproject.database.models.MyData

class AppRepository(private val databaseDao: DatabaseDao) {

    val getAllData: LiveData<List<MyData>> = databaseDao.getAllData()

    //FOR SORT
    val sortByHighPriority: LiveData<List<MyData>> = databaseDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<MyData>> = databaseDao.sortByLowPriority()

    val sortByDateDesc: LiveData<List<MyData>> = databaseDao.sortByDateDesc()
    val sortByDateAsc: LiveData<List<MyData>> = databaseDao.sortByDateAsc()

    suspend fun insertData(mydata: MyData){
        databaseDao.insertData(mydata)
    }

    suspend fun updateData(mydata: MyData){
        databaseDao.updateData(mydata)
    }

    suspend fun deleteItem(mydata: MyData){
        databaseDao.deleteItem(mydata)
    }

    suspend fun deleteAll(){
        databaseDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<MyData>> {
        return databaseDao.searchDatabase(searchQuery)
    }

}