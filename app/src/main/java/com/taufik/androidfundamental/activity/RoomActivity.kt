package com.taufik.androidfundamental.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidfundamental.adapter.RoomNoteAdapter
import com.taufik.androidfundamental.databinding.ActivityRoomBinding
import com.taufik.androidfundamental.factory.RoomViewModelFactory
import com.taufik.androidfundamental.viewmodel.RoomNoteViewModel

class RoomActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRoomBinding.inflate(layoutInflater)
    }

    private lateinit var noteAdapter: RoomNoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdapterView()
        initObserver()
        setAction()
    }

    private fun initAdapterView() = with(binding) {
        noteAdapter = RoomNoteAdapter()
        rvNotes.apply {
            layoutManager = LinearLayoutManager(this@RoomActivity)
            (layoutManager as LinearLayoutManager).reverseLayout = true
            (layoutManager as LinearLayoutManager).stackFromEnd = true
            setHasFixedSize(true)
            adapter = noteAdapter
        }
    }

    private fun initObserver() {
        val noteViewModel = obtainViewModel(this)
        noteViewModel.getAllNotes.observe(this) { noteList ->
            if (noteList != null) {
                noteAdapter.setListNotes(noteList)
            }
        }
    }

    private fun setAction() = with(binding) {
        fabNotes.setOnClickListener {
            startActivity(Intent(this@RoomActivity, RoomNoteAddUpdateActivity::class.java))
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): RoomNoteViewModel {
        val factory = RoomViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[RoomNoteViewModel::class.java]
    }
}