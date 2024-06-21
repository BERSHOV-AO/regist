package ru.nak.ied.regist.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.nak.ied.regist.entities.AGVItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(dataBase: MainDataBase) : ViewModel() {

    val dao = dataBase.getDao()
    val allAGV: LiveData<List<AGVItem>> = dao.getAllAGV().asLiveData()

    fun insertAGV(agv: AGVItem) = viewModelScope.launch {
        dao.insertAGV(agv)
    }

    fun updateAGV(agv: AGVItem) = viewModelScope.launch {
        dao.updateAGV(agv)
    }

    fun deleteAGV(id: Int) = viewModelScope.launch {
        dao.deleteAGV(id)
    }

    // класс для инициализации MainViewModel
    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}