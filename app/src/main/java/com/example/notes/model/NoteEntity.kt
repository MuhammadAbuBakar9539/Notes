package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "detail")
    var detail: String
)