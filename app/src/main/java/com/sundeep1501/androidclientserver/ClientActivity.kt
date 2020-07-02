package com.sundeep1501.androidclientserver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity

class ClientActivity : AppCompatActivity() {

    class IncomingMessageHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // server messages will appear here
        }
    }

    val messenger = Messenger(IncomingMessageHandler())


    var mServer: Messenger? = null
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mServer = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mServer = Messenger(service)

            // register for service
            Message.obtain(null, ServerService.REGISTER).also {
                it.replyTo = messenger
                mServer?.send(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
    }

    override fun onStart() {
        super.onStart()
        Intent(this@ClientActivity, ServerService::class.java).also {
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Message.obtain(null, ServerService.UNREGISTER).also {
            it.replyTo = messenger
            mServer?.send(it)
        }
        unbindService(mServiceConnection)
    }

}
