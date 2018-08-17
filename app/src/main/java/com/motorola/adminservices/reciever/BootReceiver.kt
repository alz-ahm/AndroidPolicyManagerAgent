package com.motorola.adminservices.reciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.motorola.adminservices.Policy.Policy
import com.motorola.adminservices.Policy.PolicyManager
import com.motorola.adminservices.util.Logger


/**
 * This receiver get called whenever system boots up and notifies the [PolicyManager]
 */
class BootReceiver: BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        PolicyManager.deviceRebooted()
    }
}