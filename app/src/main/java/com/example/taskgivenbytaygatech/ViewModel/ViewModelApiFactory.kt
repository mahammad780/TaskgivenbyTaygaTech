package com.example.taskgivenbytaygatech.ViewModel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskgivenbytaygatech.Repository.FromApiToDataBase

class ViewModelApiFactory(private val repository: FromApiToDataBase, private val context: Context, private val activity: AppCompatActivity ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelApi::class.java)) {
            @Suppress("UNCHECKED_CAST") return ViewModelApi(repository, context, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}