package com.example.myapp.utils

import android.content.*
import com.example.myapp.utils.Constant.Preference.PREFERENCE_NAME


class Preference(var context: Context?) {

    private val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(PREFERENCE_NAME, 0)
    private val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

    fun setStringPreference(key: String?, value: String?) {
        editor?.putString(key, value)
        editor?.commit()
        editor?.apply()
    }

    fun setIntPreference(key: String?, value: Int) {
        editor?.putInt(key, value)
        editor?.commit()
        editor?.apply()
    }

    fun setFloatPreference(key: String?, value: Float) {
        editor?.putFloat(key, value)
        editor?.commit()
        editor?.apply()
    }

    fun setLongPreference(key: String?, value: Long) {
        editor?.putLong(key, value)
        editor?.commit()
        editor?.apply()
    }

    fun setBooleanPreference(key: String?, value: Boolean) {
        editor?.putBoolean(key, value)
        editor?.commit()
        editor?.apply()
    }

    fun getStringPreference(key: String?): String {
        return sharedPreferences?.getString(key, "").toString()
    }

    fun getIntPreference(key: String?, default: Int): String {
        return sharedPreferences?.getInt(key, default).toString()
    }

    fun getFloatPreference(key: String?, default: Float): String {
        return sharedPreferences?.getFloat(key, default).toString()
    }

    fun getLongPreference(key: String?, default: Long): String {
        return sharedPreferences?.getLong(key, default).toString()
    }

    fun getBooleanPreference(key: String?, default: Boolean): Boolean? {
        return sharedPreferences?.getBoolean(key, default)
    }



}