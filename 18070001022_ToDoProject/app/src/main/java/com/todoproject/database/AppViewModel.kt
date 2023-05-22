package com.todoproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todoproject.database.models.MyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel (application: Application) : AndroidViewModel(application) {

    private val databaseDao = AppDatabase.getDatabase(
        application
    ).databaseDao()
    private val repository: AppRepository = AppRepository(databaseDao)

    val getAllData: LiveData<List<MyData>> = repository.getAllData

    //FOR SORT
    val sortByHighPriority: LiveData<List<MyData>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<MyData>> = repository.sortByLowPriority

    val sortByDateDesc: LiveData<List<MyData>> = databaseDao.sortByDateDesc()
    val sortByDateAsc: LiveData<List<MyData>> = databaseDao.sortByDateAsc()

    fun insertData(mdata: MyData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(mdata)
        }
    }

    fun updateData(mdata: MyData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(mdata)
        }
    }

    fun deleteItem(mdata: MyData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(mdata)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<MyData>> {
        return repository.searchDatabase(searchQuery)
    }

}