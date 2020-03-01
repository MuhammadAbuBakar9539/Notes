package com.example.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.repository.Repository

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(repository) as T
    }
}