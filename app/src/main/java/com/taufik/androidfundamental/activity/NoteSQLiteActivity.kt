package com.taufik.androidfundamental.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.taufik.androidfundamental.adapter.NoteAdapter
import com.taufik.androidfundamental.data.Note
import com.taufik.androidfundamental.databinding.ActivityNoteSqliteBinding
import com.taufik.androidfundamental.db.sqlite.NoteHelper
import com.taufik.androidfundamental.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NoteSQLiteActivity : AppCompatActivity() {

    private val binding: ActivityNoteSqliteBinding by lazy {
        ActivityNoteSqliteBinding.inflate(layoutInflater)
    }

    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        showData()
        fabAction()
        saveDataState(savedInstanceState)
        loadNotesAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, noteAdapter.listNotes)
    }

    private fun initView() = with(binding) {
        rvNotes.apply {
            layoutManager = LinearLayoutManager(this@NoteSQLiteActivity)
            (layoutManager as LinearLayoutManager).reverseLayout = true
            (layoutManager as LinearLayoutManager).stackFromEnd = true
            setHasFixedSize(true)
        }
    }

    private fun showData() = with(binding) {
        noteAdapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@NoteSQLiteActivity, NoteAddUpdateActivity::class.java).apply {
                    putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
                    putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                }
                resultLauncher.launch(intent)
            }
        })

        rvNotes.adapter = noteAdapter
    }

    private fun fabAction() = with(binding) {
        fabAdd.setOnClickListener {
            val intent = Intent(this@NoteSQLiteActivity, NoteAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun saveDataState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                noteAdapter.listNotes = list
            }
        }
    }

    private fun showSnackBarMessage(message: String) = with(binding) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun loadNotesAsync() = with(binding){
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE

            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()

            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            progressBar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                noteAdapter.listNotes = notes
            } else {
                noteAdapter.listNotes = ArrayList()
                showSnackBarMessage("Tidak ada data saat ini")
            }

            noteHelper.close()
        }
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        with(binding) {
            if (result.data != null) {
                when (result.resultCode) {
                    NoteAddUpdateActivity.RESULT_ADD -> {
                        val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                        noteAdapter.addItem(note)
                        rvNotes.smoothScrollToPosition(noteAdapter.itemCount - 1)
                        showSnackBarMessage("Satu item berhasil ditambahkan")
                    }

                    NoteAddUpdateActivity.RESULT_UPDATE -> {
                        val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                        val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                        noteAdapter.updateItem(position, note)
                        rvNotes.smoothScrollToPosition(position)
                        showSnackBarMessage("Satu item berhasil diperbarui")
                    }

                    NoteAddUpdateActivity.RESULT_DELETE -> {
                        val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                        noteAdapter.removeItem(position)
                        showSnackBarMessage("Satu item berhasil dihapus")
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_STATE = "com.taufik.androidfundamental.activity.EXTRA_STATE"
    }
}