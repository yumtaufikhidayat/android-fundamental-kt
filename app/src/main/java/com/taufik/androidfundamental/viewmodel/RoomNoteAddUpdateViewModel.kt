package com.taufik.androidfundamental.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.taufik.androidfundamental.database.room.NoteEntity
import com.taufik.androidfundamental.repository.RoomNoteRepository

class RoomNoteAddUpdateViewModel(application: Application): ViewModel() {

    private val mNoteRepository: RoomNoteRepository = RoomNoteRepository(application)

    fun insert(noteEntity: NoteEntity) = mNoteRepository.insert(noteEntity)
    fun update(noteEntity: NoteEntity) = mNoteRepository.update(noteEntity)
    fun delete(noteEntity: NoteEntity) = mNoteRepository.delete(noteEntity)
}