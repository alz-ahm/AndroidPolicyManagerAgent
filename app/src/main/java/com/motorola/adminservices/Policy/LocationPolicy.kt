package com.motorola.adminservices.Policy

import android.provider.Settings
import android.util.Log
import com.motorola.adminservices.util.Logger
import com.motorola.adminservices.util.ShellUtil

/**
 * This policy turns on the location and sets it to high accuracy mode
 */
object LocationPolicy: Policy(){

    override fun applyPolicy() {

        try {
            val cmds = arrayOf("cd /system/bin",
                    "settings put secure location_providers_allowed +network",
                    "settings put secure location_providers_allowed +gps"
                    )

            ShellUtil.executecmd(cmds)
            Logger.log("policy LocationPolicy applied with shell command")

        } catch (ex: Exception){
            ex.printStackTrace()

            Settings.Secure.putInt(getAppContext().contentResolver,
                    Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_HIGH_ACCURACY)
            Logger.log("policy LocationPolicy applied with api")
        }
    }

}