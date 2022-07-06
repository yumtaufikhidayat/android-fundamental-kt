package com.taufik.androidfundamental.utils

import android.content.Context
import com.taufik.androidfundamental.data.FileModel

internal object FileHelper {

    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.fileName, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, fileName: String): FileModel {
        val fileModel = FileModel()
        fileModel.apply {
            this.fileName = fileName
            data = context.openFileInput(fileName).bufferedReader().useLines {
                it.fold("") {some, text ->
                    "$some\n$text"
                }
            }
        }

        return fileModel
    }
}