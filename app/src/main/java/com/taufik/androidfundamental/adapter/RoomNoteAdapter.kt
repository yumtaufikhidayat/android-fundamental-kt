package com.taufik.androidfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidfundamental.activity.RoomNoteAddUpdateActivity
import com.taufik.androidfundamental.databinding.ItemNoteBinding
import com.taufik.androidfundamental.database.room.NoteEntity
import com.taufik.androidfundamental.utils.NoteDiffCallback

class RoomNoteAdapter: RecyclerView.Adapter<RoomNoteAdapter.ViewHolder>() {

    private val listNotes = ArrayList<NoteEntity>()
    fun setListNotes(listNotes: List<NoteEntity>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: NoteEntity) = with(binding) {
            tvItemDate.text = note.date
            tvItemTitle.text = note.title
            tvItemDescription.text = note.description

            itemView.setOnClickListener {
                val intent = Intent(it.context, RoomNoteAddUpdateActivity::class.java)
                intent.putExtra(RoomNoteAddUpdateActivity.EXTRA_NOTE, note)
                it.context.startActivity(intent)
            }
        }
    }
}