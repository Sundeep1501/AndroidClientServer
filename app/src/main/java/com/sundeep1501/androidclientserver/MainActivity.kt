package com.sundeep1501.androidclientserver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // execute tasks in background on the service
        Intent(this@MainActivity, MyIntentService::class.java).also {
            startService(it)
            startService(it)
            startService(it)
            startService(it)
        }
    }
}
