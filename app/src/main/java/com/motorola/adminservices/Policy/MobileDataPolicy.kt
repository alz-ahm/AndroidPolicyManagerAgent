package com.motorola.adminservices.Policy

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import com.motorola.adminservices.util.Logger

/**
 * This policy turns on the mobile data by using reflection.
 */
object MobileDataPolicy: Policy() {

    override fun applyPolicy() {
        try {
            val tm = getAppContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val methodSet = tm::class.java.getDeclaredMethod("setDataEnabled", Boolean::class.javaPrimitiveType)
            methodSet.invoke(tm, true)

            Logger.log("policy MobileDataPolicy applied")
        } catch (exp: Exception) {
            Logger.error("error, policy MobileDataPolicy can not be applied. check stack trace below...")
            exp.printStackTrace()
        }
    }

}