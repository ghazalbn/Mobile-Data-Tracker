package com.firstapp.datatracker

import android.content.Context
import android.content.SharedPreferences

object ProfilePreferences {

    private lateinit var sharedPreference: SharedPreferences
    fun init(context: Context){
        sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
    }

    // KEYS
    private const val limitKey: String = "limit"
    private const val notificationKey: String = "notification"

    //limit
    fun setLimit(limit: Long) {
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putLong(limitKey, limit)
        editor.apply()
    }

    fun getLimit(): Long {
        return sharedPreference.getLong(limitKey, 2147483648L )
    }

    //notification
    fun setNotification(limit: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean(notificationKey, limit)
        editor.apply()
    }

    fun getNotification(): Boolean {
        return sharedPreference.getBoolean(notificationKey, false)
    }


}