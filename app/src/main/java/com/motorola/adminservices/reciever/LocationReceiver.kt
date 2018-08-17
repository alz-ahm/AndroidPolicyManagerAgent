package com.motorola.adminservices.reciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.motorola.adminservices.Policy.PolicyManager
import com.motorola.adminservices.util.Logger

/**
 * This broadcast receiver is triggered whenever the location mode changes. It simply notifies
 * the [PolicyManager]
 */
class LocationReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        PolicyManager.locationStateChanged()
    }
}