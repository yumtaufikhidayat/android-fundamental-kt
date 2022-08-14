package com.taufik.androidfundamental.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.taufik.androidfundamental.database.room.NoteDao
import com.taufik.androidfundamental.database.room.NoteDatabase
import com.taufik.androidfundamental.database.room.NoteEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomNoteRepository(application: Application) {
    private val mNoteDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteDatabase.getDatabase(application)
        mNoteDao = db.noteDao()
    }

    val getAllNotes: LiveData<List<NoteEntity>> = mNoteDao.getAllNotes()

    fun insert(noteEntity: NoteEntity) {
        executorService.execute {
            mNoteDao.insert(noteEntity)
        }
    }

    fun delete(noteEntity: NoteEntity) {
        executorService.execute {
            mNoteDao.delete(noteEntity)
        }
    }

    fun update(noteEntity: NoteEntity) {
        executorService.execute {
            mNoteDao.update(noteEntity)
        }
    }
}