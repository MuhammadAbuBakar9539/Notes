package com.example.notes.repository

import android.app.Application
import com.example.notes.model.NoteEntity
import com.example.notes.model.NotesDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(application: Application):Repository {
    private val noteDao =NotesDatabase.getInstance(application.applicationContext).notesDao()

    override fun getNotes(): Flowable<List<NoteEntity>> {
        return noteDao.getNotes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNote(id: Int): Flowable<NoteEntity> {
        return noteDao.getNote(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addNote(noteEntity: NoteEntity): Completable {
        return noteDao.addNote(noteEntity).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateNote(noteEntity: NoteEntity): Completable {
        return noteDao.updateNote(noteEntity).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}