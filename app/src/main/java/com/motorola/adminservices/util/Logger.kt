package com.motorola.adminservices.util

import android.util.Log
import javax.security.auth.login.LoginException

const val LOG_ENABLED = false
const val LOG_TAG = "alz"
object Logger {

    fun log(logMessage: String){
        if(LOG_ENABLED)
            Log.d(LOG_TAG, logMessage)
    }

    fun error(logMessage: String){
        if(LOG_ENABLED)
            Log.e(LOG_TAG, logMessage)
    }
}

