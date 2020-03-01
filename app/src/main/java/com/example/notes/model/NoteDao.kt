package com.example.notes.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NoteDao {

    @Query("SELECT * FROM tbl_note")
    fun getNotes(): Flowable<List<NoteEntity>>

    @Query("SELECT * FROM tbl_note WHERE id == :id")
    fun getNote(id:Int): Flowable<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(noteEntity: NoteEntity): Completable

    @Update
    fun updateNote(noteEntity: NoteEntity):Completable
}