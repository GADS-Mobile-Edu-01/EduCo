package com.android.educo.utils

import com.pixplicity.easyprefs.library.Prefs

object PrefsUtil{
    const val PREF_NAME = "PrefName"

    fun String.setUserName(){
        Prefs.putString(PREF_NAME,this)
    }

    fun getUserName() : String{
        return Prefs.getString(PREF_NAME,"Not set")
    }
}