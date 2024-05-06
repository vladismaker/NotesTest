package com.application.notestest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.notestest.App
import com.application.notestest.dataclasses.DataNotes
import com.application.notestest.repositories.RepositoryNotesImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel: ViewModel(){

    val liveDataArrayNotes = MutableLiveData<List<DataNotes>>()
    val liveDataNote = MutableLiveData<DataNotes>()
    val liveDataEmpty = MutableLiveData<Boolean>()

    private val notesRepository = RepositoryNotesImpl.getInstance()

    fun getArrayNotes(){
        App.ioScope.launch{
            val dataList = async { notesRepository.getRepositoryArrayNotes() }.await()
            if (dataList.isEmpty()){
                liveDataEmpty.postValue(true)
            }else{
                liveDataArrayNotes.postValue(dataList)
                liveDataEmpty.postValue(false)
            }
        }
    }

    fun setFullNote(dataNotes: DataNotes) {
        liveDataNote.value = dataNotes
    }

    fun changeArrayNotes(id:Int, title: String, description: String, favorite:Boolean){
        App.ioScope.launch{
            notesRepository.getRepositoryUpdateById(id, title, description, favorite)
            val dataListNew = async { notesRepository.getRepositoryArrayNotes() }.await()
            liveDataArrayNotes.postValue(dataListNew)
        }
    }

    fun addNote(title: String, description: String, favorite:Boolean){
        App.ioScope.launch{
            notesRepository.getRepositoryAddNote(title, description, favorite)
            val dataListNew = async { notesRepository.getRepositoryArrayNotes() }.await()
            liveDataArrayNotes.postValue(dataListNew)
        }
    }



    fun deleteNote(id: Int){
        App.ioScope.launch{
            notesRepository.getRepositoryDeleteNote(id)
            val dataListNew = async { notesRepository.getRepositoryArrayNotes() }.await()
            liveDataArrayNotes.postValue(dataListNew)
        }
    }
}