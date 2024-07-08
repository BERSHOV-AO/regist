package ru.nak.ied.regist.api

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Body
import ru.nak.ied.regist.entities.User
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainApi: MainApi
) {
    //val userList = mutableStateOf(emptyList<User>())


    suspend fun getAllUsers() : List<User> {
        return mainApi.getAllUsers()
    }

//    init {
//        viewModelScope.launch {
//            userList.value = mainApi.getAllUsers()
//        }
//    }

//    fun existenceUser(login: String, pass: String) = viewModelScope.launch {
//        mainApi.getUserExistence(login, pass);
//    }
}