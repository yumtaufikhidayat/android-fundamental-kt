package com.taufik.androidfundamental.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidfundamental.viewmodel.RoomNoteAddUpdateViewModel
import com.taufik.androidfundamental.viewmodel.RoomNoteViewModel

class RoomViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomNoteViewModel::class.java)) {
            return RoomNoteViewModel(application) as T
        } else if (modelClass.isAssignableFrom(RoomNoteAddUpdateViewModel::class.java)) {
            return RoomNoteAddUpdateViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

        companion object {
            @Volatile
            private var INSTANCE: RoomViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): RoomViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(RoomViewModelFactory::class.java) {
                        INSTANCE = RoomViewModelFactory(application)
                    }
                }

                return INSTANCE as RoomViewModelFactory
            }
        }
}