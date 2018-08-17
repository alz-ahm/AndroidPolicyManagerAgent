package com.motorola.adminservices.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.motorola.adminservices.R


/**
 * Our project doesn't need any activity and has no visual page for user to interact with
 * This Activity however can be used for testing the code. To see the activity uncomment
 * the intent filters in the manifest file
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
