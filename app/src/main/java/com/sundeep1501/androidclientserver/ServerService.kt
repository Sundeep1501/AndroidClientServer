package com.sundeep1501.androidclientserver

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger

class ServerService : Service() {

    companion object {
        const val REGISTER = 1
        const val UNREGISTER = 2
        const val BROADCAST = 3
    }

    class IncomingMessageHandler(private val clients: ArrayList<Messenger>) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                REGISTER -> {
                    clients.add(msg.replyTo)
                }
                UNREGISTER -> {
                    clients.remove(msg.replyTo)
                }
                BROADCAST -> {
                    clients.forEach { client ->
                        obtainMessage().also { broadCastMsg ->
                            broadCastMsg.data = msg.data
                            client.send(broadCastMsg)
                        }
                    }
                }
            }
        }
    }

    private lateinit var myMessenger: Messenger
    private lateinit var clients: ArrayList<Messenger>

    override fun onCreate() {
        super.onCreate()
        clients = ArrayList()
        myMessenger = Messenger(IncomingMessageHandler(clients))
    }

    override fun onBind(intent: Intent): IBinder {
        return myMessenger.binder
    }
}