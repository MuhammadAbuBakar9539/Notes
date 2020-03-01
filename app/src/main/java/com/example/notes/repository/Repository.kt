package com.example.notes.repository


import com.example.notes.model.NoteEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface Repository {

    fun getNotes(): Flowable<List<NoteEntity>>

    fun getNote(id:Int): Flowable<NoteEntity>

    fun addNote(noteEntity: NoteEntity): Completable

    fun updateNote(noteEntity: NoteEntity): Completable
}