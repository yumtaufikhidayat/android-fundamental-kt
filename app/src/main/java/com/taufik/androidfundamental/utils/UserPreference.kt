package com.taufik.androidfundamental.utils

import android.content.Context
import com.taufik.androidfundamental.data.UserModel

class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preference.edit()
        editor.apply {
            putString(NAME, value.name)
            putString(EMAIL, value.email)
            putInt(AGE, value.age)
            putString(PHONE_NUMBER, value.phoneNumber)
            putBoolean(LOVE_MU, value.isLove)
        }
        editor.apply()
    }

    fun getUser(): UserModel {
        val model = UserModel()
        model.apply {
            name = preference.getString(NAME, "")
            email = preference.getString(EMAIL, "")
            age = preference.getInt(AGE, 0)
            phoneNumber = preference.getString(PHONE_NUMBER, "")
            isLove = preference.getBoolean(LOVE_MU, false)
        }
        return model
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "isLove"
    }
}