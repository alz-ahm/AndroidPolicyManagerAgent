package com.motorola.adminservices.workmanager

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.motorola.adminservices.util.Logger
import java.util.concurrent.TimeUnit

const val TAG_APPLY_ALL_POLICIES = "TAG_APPLY_ALL_POLICIES"
const val DELAY_BEFORE_RUN_MINS = 15L

/**
 * This class simply enqueues the [PolicyWorker] with [WorkManager] apis, [WorkManager] will then
 * takes care of handling the work. It will uses different technologies based on os version and
 * availability of  different methods on the device.
 */

object PolicyWorkManager {

    fun planNextJob(){
        Logger.log("planning next work after $DELAY_BEFORE_RUN_MINS minutes from now")

        //remove previously added jobs if any.
        WorkManager.getInstance().cancelAllWorkByTag(TAG_APPLY_ALL_POLICIES)

        val oneTimeWork = OneTimeWorkRequestBuilder<PolicyWorker>()
                .setInitialDelay(DELAY_BEFORE_RUN_MINS, TimeUnit.MINUTES)
                .addTag(TAG_APPLY_ALL_POLICIES)
                .build()

        WorkManager.getInstance().enqueue(oneTimeWork)
    }
}