package com.motorola.adminservices.Policy

import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import android.bluetooth.BluetoothAdapter
import com.motorola.adminservices.util.Logger


/**
 * This policy turns off the bluetooth to reduce the battery usage.
 * It checks if the bluetooth is on and if so it will turn it off
 */
object BluetoothPolicy : Policy() {

    override fun applyPolicy() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
            Logger.log("policy BluetoothPolicy applied with api")
        }
    }
}



