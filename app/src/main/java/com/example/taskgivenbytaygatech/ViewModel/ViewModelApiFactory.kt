package com.example.taskgivenbytaygatech.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskgivenbytaygatech.Repository.FromApiToDataBase

class ViewModelApiFactory(private val repository: FromApiToDataBase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelApi::class.java)) {
            @Suppress("UNCHECKED_CAST") return ViewModelApi(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}