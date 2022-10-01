package com.taufik.androidfundamental.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityTaskBackStackBinding

class TaskBackStackActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "Navigation channel"
        const val VIBRATE_1000 = 1000L
    }

    private val binding by lazy {
        ActivityTaskBackStackBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOpenDetail.setOnClickListener(this)
        showNotification(this, getString(R.string.notification_title), getString(R.string.notification_message), 110)
    }

    private fun showNotification(context: Context, title: String, message: String, notificationId: Int) {
        val notificationDetailIntent = Intent(this, DetailTaskBackStackActivity::class.java)
        notificationDetailIntent.apply {
            putExtra(DetailTaskBackStackActivity.EXTRA_TITLE, title)
            putExtra(DetailTaskBackStackActivity.EXTRA_MESSAGE, message)
        }

        val pendingIntent = TaskStackBuilder.create(this).run {
            addParentStack(DetailTaskBackStackActivity::class.java)
            addNextIntent(notificationDetailIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_outline_notification)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setVibrate(longArrayOf(VIBRATE_1000, VIBRATE_1000, VIBRATE_1000, VIBRATE_1000))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(VIBRATE_1000, VIBRATE_1000, VIBRATE_1000, VIBRATE_1000, VIBRATE_1000)
            }
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnOpenDetail -> {
                val intent = Intent(this, DetailTaskBackStackActivity::class.java)
                intent.apply {
                    putExtra(DetailTaskBackStackActivity.EXTRA_TITLE, getString(R.string.detail_title))
                    putExtra(DetailTaskBackStackActivity.EXTRA_MESSAGE, getString(R.string.detail_message))
                }
                startActivity(intent)
            }
        }
    }
}