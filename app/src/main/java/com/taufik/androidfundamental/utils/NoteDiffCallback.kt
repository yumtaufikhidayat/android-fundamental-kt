package com.taufik.androidfundamental.utils

import androidx.recyclerview.widget.DiffUtil
import com.taufik.androidfundamental.db.room.NoteEntity

class NoteDiffCallback(
    private val mOldNoteList: List<NoteEntity>,
    private val mNewNoteList: List<NoteEntity>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldNoteList.size
    override fun getNewListSize(): Int = mNewNoteList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int)
    : Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
    }
}