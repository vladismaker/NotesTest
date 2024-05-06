package com.application.notestest.repositories

import com.application.notestest.App
import com.application.notestest.dataclasses.DataNotes
import com.application.notestest.room.DatabaseBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.TimeZone

class RepositoryNotesImpl private constructor() {
    private val db = DatabaseBuilder.getInstance(App.context).dataNotesDao()

    companion object {
        private var INSTANCE: RepositoryNotesImpl? = null


        fun getInstance(): RepositoryNotesImpl = INSTANCE ?: kotlin.run {
            INSTANCE = RepositoryNotesImpl()
            INSTANCE!!
        }
    }

    suspend fun getRepositoryArrayNotes():MutableList<DataNotes>{
        return db.getAllDataNotes()
    }

    suspend fun getRepositoryUpdateById(id:Int, title: String, description: String, favorite:Boolean){
        db.updateById(id, title, description, favorite)
    }

    suspend fun getRepositoryAddNote(title: String, description: String, favorite:Boolean){
        db.insert(DataNotes(Random().nextInt(999999), title, description, favorite, getLocalDate()))
    }

    private fun getLocalDate(): String {
        val moscowTimeZone = TimeZone.getTimeZone("Europe/Moscow")
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        dateFormat.timeZone = moscowTimeZone
        val currentDateTime = Date()
        return dateFormat.format(currentDateTime)
    }


    fun getRepositoryDeleteNote(id:Int){
        db.deleteById(id)
    }
}