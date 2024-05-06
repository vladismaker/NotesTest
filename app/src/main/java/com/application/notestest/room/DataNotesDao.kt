package com.application.notestest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.application.notestest.dataclasses.DataNotes

@Dao
interface DataNotesDao {
    @Insert
    suspend fun insert(dataNotes: DataNotes)

    @Insert
    suspend fun insertAll(dataNotesList: List<DataNotes>)

    @Query("SELECT * FROM data_notes_table")
    suspend fun getAllDataNotes(): MutableList<DataNotes>

    @Update
    suspend fun updateDataList(dataList: List<DataNotes>)

    @Query("UPDATE data_notes_table SET title = :newTitle, description = :newDescription, favorite = :newFavorite WHERE id = :id")
    suspend fun updateById(id: Int, newTitle: String, newDescription: String, newFavorite: Boolean)

    @Query("DELETE FROM data_notes_table WHERE id = :id")
    fun deleteById(id: Int)
}