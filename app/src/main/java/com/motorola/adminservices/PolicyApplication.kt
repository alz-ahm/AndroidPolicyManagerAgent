package com.motorola.adminservices

import android.app.Application
import com.motorola.adminservices.workmanager.PolicyWorkManager

/**
 * This is the Application class for the project. This class stores an instance of itself so we can
 * have application context in any place of the application we need.
 * Since we are using application context we don't have to worry about memory leak.
 */
class PolicyApplication: Application(){

    companion object {
        lateinit var appContext: PolicyApplication
    }

    override fun onCreate() {
        super.onCreate()
        appContext =  this
    }
}