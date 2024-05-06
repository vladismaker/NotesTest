package com.application.notestest.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_notes_table")
data class DataNotes(
    @PrimaryKey val id:Int,
    var title:String = "",
    var description:String = "",
    var favorite:Boolean = false,
    var date:String="",
)
