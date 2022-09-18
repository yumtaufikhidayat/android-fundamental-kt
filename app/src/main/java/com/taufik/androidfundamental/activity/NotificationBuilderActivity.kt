package com.taufik.androidfundamental.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityNotificationBuilderBinding

class NotificationBuilderActivity : AppCompatActivity() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }

    private val binding by lazy {
        ActivityNotificationBuilderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sendNotification()
    }

    private fun sendNotification() = with(binding) {
        btnNotification.setOnClickListener {
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val mBuilder = NotificationCompat.Builder(this@NotificationBuilderActivity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_outline_notification)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_outline_notification))
                .setContentTitle(resources.getString(R.string.content_title))
                .setContentText(resources.getString(R.string.content_text))
                .setSubText(resources.getString(R.string.subtext))
                .setAutoCancel(false)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = CHANNEL_NAME

                mBuilder.setChannelId(CHANNEL_ID)
                mNotificationManager.createNotificationChannel(channel)
            }

            val notification = mBuilder.build()
            mNotificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}