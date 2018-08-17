package com.motorola.adminservices.Policy

import android.content.Context
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.motorola.adminservices.PolicyApplication
import com.motorola.adminservices.R
import com.motorola.adminservices.util.Logger
import com.motorola.adminservices.workmanager.PolicyWorkManager

const val MS_DELAY_BEFORE_SENDING_BROADCAST = 3000L
const val BROADCAST_ACTION_NAME = "ALL_POLICIES_APPLIED"
const val MS_DELAY_AFTER_REBOOT_TO_RUN_POLICIES = 2000L

/**
 * This class is responsible for managing when to run policies, Broadcast receivers simply call the
 * public method of this class to notify it about the state changes.
 */

object PolicyManager {
    /**
     * Simply creates a list of policies <b>in order</b> and calls applyPolicy on
     * each of them, After applying all policies it schedule another work to get run
     * in the feature and then broadcast an event for other apps.
     */
    fun applyAllPolicies(){
        try {
            /**
             * Its better to let all policies run and apply location and mobile data policies
             * at the end.
             */
            val policyList = listOf(AirplaneModePolicy, BluetoothPolicy,
                    LocationPolicy, MobileDataPolicy)

            policyList.forEach { it.applyPolicy() }
            broadcast()

        } catch (ex: Exception){
            ex.printStackTrace()

        } finally {
            /**
             * no matter what happens we always want to plan the next work to insure that this loop
             * doesn't get broken.
             */
            PolicyWorkManager.planNextJob()
        }

    }

    /**
     * Whenever the bluetooth gets turned on we run all the policies and turn off the
     * bluetooth. This is done to save battery usage.
     */
    fun bluetoothStateChanged(){
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
            Logger.log("bluetooth turned on, applying policies...")
            applyAllPolicies()
        }
    }

    /**
     * Whenever the airplane mode gets turn on we apply all policies to turn it off.
     */
    fun airplaneModeStateChanged(newState: Boolean){
        if(newState) {
            Logger.log("airplane mode turned on, applying policies...")
            applyAllPolicies()
        }
    }

    /**
     * We always want LOCATION_MODE_HIGH_ACCURACY to be enabled so if gps or network or both are disabled
     * We apply all the policies again to get it fixed.
     * We cannot activate both network and gps at the same time and each change causes the new broadcast
     * to fire that might cause a loop, so to avoid this loop we will wait for a while, then check
     * if we have to apply the policies.
     */
    fun locationStateChanged(){
        Thread.sleep(3000)

        val locationManager = PolicyApplication.appContext
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager? ?: return

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        Logger.log("isGpsEnables: $isGpsEnabled - is network enabled: $isNetworkEnabled")

        if(!isGpsEnabled || !isNetworkEnabled){
            Logger.log("accurate location is turned off, applying policies...")
            applyAllPolicies()
            Toast.makeText(PolicyApplication.appContext, R.string.message_locaiton_turn_off, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Whenever the system gets rebooted we wait for a while and then apply all policies to make sure
     * everything is set properly.
     * Waiting is because reboot may be received by other apps as well, so we let them do there job
     * first, then we do our job and make sure our job overrides anything that other app may or may
     * not done just to be sure.
     */
    fun deviceRebooted(){
        Logger.log("system rebooted, applying policies after $MS_DELAY_AFTER_REBOOT_TO_RUN_POLICIES milliseconds from now...")
        Handler().postDelayed({
            PolicyManager.applyAllPolicies()

        }, MS_DELAY_AFTER_REBOOT_TO_RUN_POLICIES)
    }

    /**
     * This function broadcast that all the policies are applied. So other apps can listen to this and
     * react accordingly.
     * This function waits for a while just to be sure the executed policies has been enabled.
     */
    private fun broadcast() {
        Handler(Looper.getMainLooper()).postDelayed({
            val actionName = PolicyApplication.appContext.packageName + "." + BROADCAST_ACTION_NAME
            val intent = Intent().setAction(actionName)
            PolicyApplication.appContext.sendBroadcast(intent)

            Logger.log("broadcast $actionName fired...")

        }, MS_DELAY_BEFORE_SENDING_BROADCAST)

    }
}

