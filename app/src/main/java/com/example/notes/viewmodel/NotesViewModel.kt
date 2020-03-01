package com.example.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.model.NoteEntity
import com.example.notes.repository.Repository
import io.reactivex.disposables.CompositeDisposable

class NotesViewModel(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    //for getNotes
    private val notesObservable = MutableLiveData<List<NoteEntity>>()
    private val notesErrorObservable = MutableLiveData<String>()
    //forgetNote
    private val noteObservable = MutableLiveData<NoteEntity>()
    private val noteErrorObservable = MutableLiveData<String>()
    //for addNote
    private val addSuccessObservable = MutableLiveData<Boolean>()
    //for updateNote
    private val updateSuccessObservable = MutableLiveData<Boolean>()

    fun getNotes() {
        compositeDisposable.add(
            repository.getNotes().subscribe(
                { notes -> notesObservable.value = notes },
                { notesError -> notesErrorObservable.value = notesError.message })
        )
    }

    fun getNote(id: Int) {
        compositeDisposable.add(
            repository.getNote(id).subscribe(
                { note -> noteObservable.value = note },
                { noteError -> noteErrorObservable.value = noteError.message })
        )
    }

    fun addNote(noteEntity: NoteEntity) {
        compositeDisposable.add(
            repository.addNote(noteEntity).subscribe(
                { addSuccessObservable.value = true },
                { addSuccessObservable.value = false })
        )
    }

    fun updateNote(noteEntity: NoteEntity) {
        compositeDisposable.add(
            repository.updateNote(noteEntity).subscribe(
                { updateSuccessObservable.value = true },
                { updateSuccessObservable.value = false })
        )
    }

    fun getNotesObservable(): LiveData<List<NoteEntity>> = notesObservable
    fun getNotesErrorObservable(): MutableLiveData<String> = notesErrorObservable

    fun getNoteObservable(): MutableLiveData<NoteEntity> = noteObservable
    fun getNoteErrorObservable(): MutableLiveData<String> = noteErrorObservable

    fun getAddSuccessObservable(): MutableLiveData<Boolean> = addSuccessObservable

    fun getUpdateSuccessObservable(): MutableLiveData<Boolean> = updateSuccessObservable

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}