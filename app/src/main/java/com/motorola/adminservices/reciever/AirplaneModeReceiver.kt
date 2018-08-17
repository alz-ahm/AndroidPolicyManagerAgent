package com.motorola.adminservices.reciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.motorola.adminservices.Policy.PolicyManager

/**
 * This broadcast receiver is triggered whenever the airplane mode changes. It simply notifies
 * the [PolicyManager]
 */
class AirplaneModeReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val isAirplaneModeOn = intent.getBooleanExtra("state", false)
        PolicyManager.airplaneModeStateChanged(isAirplaneModeOn)
    }
}
