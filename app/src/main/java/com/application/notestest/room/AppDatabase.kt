package com.application.notestest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.notestest.dataclasses.DataNotes

@Database(entities = [DataNotes::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataNotesDao(): DataNotesDao
}