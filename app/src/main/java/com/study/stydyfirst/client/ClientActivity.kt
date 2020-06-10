package com.study.stydyfirst.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.stydyfirst.Bean.PersonBean
import com.study.stydyfirst.R
import com.study.stydyfirst.server.PersonManager
import com.study.stydyfirst.server.PersonManagerService
import com.study.stydyfirst.server.PersonManagerStub
import kotlinx.android.synthetic.main.activity_client.*

/**
 * 本地client
 */
class ClientActivity : AppCompatActivity() {

    /** The primary interface we will be calling on the service.  */
    private var mService: PersonManager? = null

    /**
     * 是否绑定的标志
     */
    private var isBound = false

    /**
     * Class for interacting with the main interface of the service.
     */
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = PersonManagerStub.asInterface(service)//将service转成远程服务代理对象，并调用他的方法
            isBound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        // Bind to LocalService
        addListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(mConnection)
        }
    }

    private fun addListener() {
        btn_getperson.setOnClickListener {
            tv.setText(mService?.persons.toString())
        }
        btn_add.setOnClickListener {
            var person = PersonBean()
            person.name = "刘德华"
            mService?.addPerson(person)
        }
        btn_delete.setOnClickListener {
            var person = PersonBean()
            person.name = "刘德华"
            mService?.deletePerson(person)
        }
        btn_bind.setOnClickListener {
            Intent(this, PersonManagerService::class.java).also { intent ->
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
                //startService(intent)
            }
        }
    }
}
