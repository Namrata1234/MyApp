package com.example.myapp.viewmodel

import androidx.lifecycle.*
import com.example.myapp.api.ApiResponse
import com.example.myapp.api.Coroutines
import com.example.myapp.api.repository.UsersRepository

class UsersViewModel(private val repository: UsersRepository) : ViewModel() {

    private val setUsersApiResponse = MutableLiveData<ApiResponse?>()

    fun apiCallForUsers(page: Int?) {
        Coroutines.ioThenMain({ repository.apiCallForUsers(page) }) { response -> setUsersApiResponse.value = response }
    }

    val getUsersApiResponse: LiveData<ApiResponse?> get() = setUsersApiResponse

}