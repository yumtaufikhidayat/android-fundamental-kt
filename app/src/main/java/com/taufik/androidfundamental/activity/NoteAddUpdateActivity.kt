package com.taufik.androidfundamental.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.data.Note
import com.taufik.androidfundamental.databinding.ActivityNoteAddUpdateBinding
import com.taufik.androidfundamental.database.sqlite.DatabaseContract
import com.taufik.androidfundamental.database.sqlite.NoteHelper
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityNoteAddUpdateBinding by lazy {
        ActivityNoteAddUpdateBinding.inflate(layoutInflater)
    }

    private var isEdit = false
    private var note: Note? = null
    private var position = 0

    private lateinit var noteHelper: NoteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNoteHelper()
        initNote()
        initActionBarTitle()
        initButton()
    }

    private fun initNoteHelper() {
        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()
    }

    private fun initNote() {
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }
    }

    private fun initActionBarTitle() = with(binding) {
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            note?.let {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.apply {
            title = actionBarTitle
            setDisplayHomeAsUpEnabled(true)
        }

        btnSubmit.text = btnTitle
    }

    private fun initButton() = with(binding) {
        btnSubmit.setOnClickListener(this@NoteAddUpdateActivity)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun showAlertDialog(dialogType: Int) {
        val isDialogClose = dialogType == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogTitle = "Hapus"
            dialogMessage = "Apakah anda ingin menghapus item ini?"
        }

        AlertDialog.Builder(this).apply {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@NoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
            create()
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSubmit -> {
                with(binding) {
                    val title = etTitle.text.toString().trim()
                    val description = etDescription.text.toString().trim()

                    title.ifEmpty {
                        etTitle.error = "Field can not be blank"
                        return
                    }

                    description.ifEmpty {
                        etDescription.error = "Field can not be blank"
                    }

                    note?.apply {
                        this.title = title
                        this.description = description
                    }

                    val intent = Intent().apply {
                        putExtra(EXTRA_NOTE, note)
                        putExtra(EXTRA_POSITION, position)
                    }

                    val values = ContentValues().apply {
                        put(DatabaseContract.NoteColumns.TITLE, title)
                        put(DatabaseContract.NoteColumns.DESCRIPTION, description)
                    }

                    if (isEdit) {
                        val result = noteHelper.update(note?.id.toString(), values).toLong()
                        if (result > 0) {
                            setResult(RESULT_UPDATE, intent)
                            finish()
                        } else {
                            Toast.makeText(this@NoteAddUpdateActivity, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        note?.date = getCurrentDate()
                        values.put(DatabaseContract.NoteColumns.DATE, getCurrentDate())

                        val result = noteHelper.insert(values)

                        if (result > 0) {
                            note?.id = result.toInt()
                            setResult(RESULT_ADD, intent)
                            finish()
                        } else {
                            Toast.makeText(this@NoteAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_NOTE = "com.taufik.androidfundamental.activity.EXTRA_NOTE"
        const val EXTRA_POSITION = "com.taufik.androidfundamental.activity.EXTRA_POSITION"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}