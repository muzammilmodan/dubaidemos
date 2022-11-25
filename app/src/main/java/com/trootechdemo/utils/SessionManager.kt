package com.trootechdemo.utils

import android.content.Context



/**
 * Local data stored in Shared Preferences...
 **/
object SessionManager {

    private val PREFS_NAME = "App Preference"

    private val PARAM_SUB_CATEGORY_ALL= "subCategory_all"

    //.........................
    fun clearAppSession(context: Context) {
        try {
            val preferences =
                context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }


}
