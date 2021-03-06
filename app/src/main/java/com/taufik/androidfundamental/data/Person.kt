package com.taufik.androidfundamental.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val title: String?,
    val name: String?,
    val age: Int?,
    val email: String?,
    val city: String?
): Parcelable