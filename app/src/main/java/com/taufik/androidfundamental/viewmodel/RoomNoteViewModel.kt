package com.taufik.androidfundamental.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.taufik.androidfundamental.db.room.NoteEntity
import com.taufik.androidfundamental.repository.RoomNoteRepository

class RoomNoteViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: RoomNoteRepository = RoomNoteRepository(application)

    val getAllNotes: LiveData<List<NoteEntity>> = mNoteRepository.getAllNotes
}