package com.example.taskgivenbytaygatech.Adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskgivenbytaygatech.Repository.FromDataBase

class AppViewModelFactoryForDatabase(private val repository: FromDataBase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModelForDatabase::class.java)) {
            @Suppress("UNCHECKED_CAST") return AppViewModelForDatabase(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}