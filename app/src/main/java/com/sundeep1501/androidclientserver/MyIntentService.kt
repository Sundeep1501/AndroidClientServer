package com.sundeep1501.androidclientserver

import android.app.Service
import android.content.Intent
import android.os.*

/**
 * This service accepts the intents and processes them on worker thread, one after the other.
 */
class MyIntentService : Service() {

    private lateinit var handler: IncomingMessageHandler

    inner class IncomingMessageHandler(looper: Looper) : Handler(looper) {
        // messages will be handled on the looper thread
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            println(msg.arg1.toString()+" task started")
            try {
                Thread.sleep(2000)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            println(msg.arg1.toString()+" task completed")
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        println(MyIntentService::class.java.simpleName + " created")
        // HandlerThread creates a new thread, with it's own looper
        val handlerThread = HandlerThread("")
        // start the thread
        handlerThread.start()


        handler = IncomingMessageHandler(handlerThread.looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val msg = handler.obtainMessage()
        msg.arg1 = startId

        handler.sendMessage(msg)
        println(msg.arg1.toString()+" task received")
        return START_STICKY
    }

    // binding to the service is not allowed, so we return null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        println(MyIntentService::class.java.simpleName + " destroyed")
    }

}
