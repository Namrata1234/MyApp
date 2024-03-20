package com.example.myapp.api.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.api.repository.UsersRepository
import com.example.myapp.viewmodel.UsersViewModel


class UsersViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(repository) as T
    }

}