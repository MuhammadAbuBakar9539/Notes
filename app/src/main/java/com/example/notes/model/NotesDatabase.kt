package com.example.notes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                var instance: NotesDatabase? = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "Notes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}