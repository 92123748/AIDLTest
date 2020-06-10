package com.study.stydyfirst.server

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.study.stydyfirst.Bean.PersonBean
import com.study.stydyfirst.R

/**
 * 远程service
 */
class PersonManagerService : Service() {
    /**
     * 创建一个数组用于管理人员  var 声明可变变量，val声明不可变变量 相当于final
     */
    private var personss: ArrayList<PersonBean> = ArrayList()
    val CHANNEL_ID = "personmanager"
    val notificationId = 1
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        //初始化通知栏，
        showNotification()
    }

    /**
     * 将当前人数显示通过通知栏显示出来
     */
    fun showNotification() {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("远程service通知栏")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("当前的人数是：" + personss.size + "    人员信息：" + personss.toString())
            )
            .setSmallIcon(R.mipmap.ic_launcher)//不设置smallicon，文字信息不生效，我服了
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            //notify(notificationId, builder.build())
            startForeground(notificationId, builder.build())
        }
    }

    /**
     * 首先创建通知渠道，再进行通知显示
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent): IBinder {
//        TODO("Return the communication channel to the service.")

        return binder
    }

    private val binder = object : PersonManagerStub() {
        override fun addPerson(personBean: PersonBean?) {
            if (personBean != null) {
                personss.add(personBean)
                Log.d("Server", "添加" + personBean.name)
                showNotification()
            };
        }

        override fun deletePerson(personBean: PersonBean?) {
            personss.remove(personBean)
            Log.d("Server", "删除")
            showNotification()
        }

        override fun getPersons(): List<PersonBean> {
            Log.d("Server", "getPersons")
            return personss
        }
    }
}
