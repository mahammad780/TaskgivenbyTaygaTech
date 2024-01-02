package com.example.taskgivenbytaygatech.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskgivenbytaygatech.Repository.FromDataBase

class ViewModelDatabaseFactory(private val repository: FromDataBase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelDatabase::class.java)) {
            @Suppress("UNCHECKED_CAST") return ViewModelDatabase(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}