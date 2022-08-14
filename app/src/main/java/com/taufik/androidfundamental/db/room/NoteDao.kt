package com.taufik.androidfundamental.db.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noteEntity: NoteEntity)

    @Update
    fun update(noteEntity: NoteEntity)

    @Delete
    fun delete(noteEntity: NoteEntity)

    @Query("SELECT * FROM tbl_note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<NoteEntity>>
}