package com.motorola.adminservices.workmanager

import android.util.Log
import androidx.work.Worker
import com.motorola.adminservices.Policy.PolicyManager


/**
 * This worker is simply asks [PolicyManager] to apply all the policies to the system
 * This can be used to schedule the work to be run latter.
 */
class PolicyWorker : Worker() {

    override fun doWork(): Result {
        PolicyManager.applyAllPolicies()
        return Result.SUCCESS
    }
}