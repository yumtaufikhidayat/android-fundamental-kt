package com.taufik.androidfundamental.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_db"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return INSTANCE as NoteDatabase
        }
    }
}