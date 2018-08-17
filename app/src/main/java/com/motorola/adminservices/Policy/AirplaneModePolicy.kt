package com.motorola.adminservices.Policy

import android.app.Application
import android.content.Context
import android.provider.Settings
import com.motorola.adminservices.util.Logger
import com.motorola.adminservices.util.ShellUtil


/**
 * This policy will turn off the airplane mode.
 */

object AirplaneModePolicy: Policy() {

    override fun applyPolicy() {
        try {
            val cmds = arrayOf("cd /system/bin", "settings put global airplane_mode_on 0",
                    "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false"
                    )
            ShellUtil.executecmd(cmds)
            Logger.log("policy AirplaneModePolicy applied will shell command")

        } catch (ex: Exception) {
            ex.printStackTrace()

            /**
             * we can not turn off airplane mode without the shell, however we will config it in a way
             * that even if the airplane mode is on, we can have  access to location and data
             * Airplane has two config one airplane_mode_radios that determines what radius (bluetooth, wifi, nfc, wimax)
             * should be effected if we turn on the airplane mode. However in my testing it doesn't work for cell so
             * even with excluding the cell from this config it still gets off.
             * The second option is airplane_mode_toggleable_radios that indicate what radius can be turn on after
             * airplane mode gets on. We include all so after applying this policy we can apply other policies to
             * turn on location and data
             * Note: this policy seems to not have effect on data in companies devices, but I still
             * Keep the code as a B Plan
             * Note: This policy should be run before all the policies related to data and location.
             */

            Settings.Global.putString(getAppContext().contentResolver, "airplane_mode_radios", "bluetooth,nfc")
            Settings.Global.putString(getAppContext().contentResolver, "airplane_mode_toggleable_radios", "cell,bluetooth,nfc,wimax")
            Logger.log("policy AirplaneModePolicy applied with api")
        }
    }
}