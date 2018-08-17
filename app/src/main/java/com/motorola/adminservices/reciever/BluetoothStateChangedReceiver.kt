package com.motorola.adminservices.reciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.motorola.adminservices.Policy.PolicyManager
import com.motorola.adminservices.util.Logger

/**
 * This broadcast receiver is triggered whenever Bluetooth state changes. It simply
 * notifies the [PolicyManager] about the state change.
 */
class BluetoothStateChangedReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        PolicyManager.bluetoothStateChanged()
    }
}
