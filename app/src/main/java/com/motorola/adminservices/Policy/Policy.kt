package com.motorola.adminservices.Policy

import android.app.Application
import com.motorola.adminservices.PolicyApplication

/**
 * This is a base class for all policies, All the policies in the system should be
 * inherit from this. By using this class, policy logic gets separated from the
 * the logic that executes the policies
 */
abstract class Policy {
    /**
     * This function is a place for subclasses to implement the logic for
     * Their policies. If they require some configuration they can configure
     * the class during initialization and before this method gets run.
     */
    abstract fun applyPolicy()

    /**
     * A method to get the application class that can be used by sub classes.
     * Since context is required for applying most policies.
     * @return An instance of application class. This function gets the instance from
     * the application class.
     */
    protected fun getAppContext(): Application {
        return PolicyApplication.appContext
    }
}


