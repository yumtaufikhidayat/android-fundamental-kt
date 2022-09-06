package com.taufik.androidfundamental.activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityBroadcastReceiverBinding
import com.taufik.androidfundamental.service.DownloadService

class BroadcastReceiverActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBroadcastReceiverBinding.inflate(layoutInflater)
    }

    private lateinit var downloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initReceiver()
        setAction()
    }

    private fun initReceiver() {
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(DownloadService.TAG, "onReceive: Download selesai")
                showToast("Download selesai")
            }
        }

        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    private fun setAction() = with(binding) {
        btnCheckPermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
        }

        btnDownload.setOnClickListener {
            startService(Intent(this@BroadcastReceiverActivity, DownloadService::class.java))
        }
    }

    private var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            showToast("Sms permission receiver diterima")
        } else {
            showToast("Sms permission receiver ditolak")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SMS_REQUEST_CODE -> {
                when (PackageManager.PERMISSION_GRANTED) {
                    grantResults[0] -> showToast("Sms permission receiver diterima")
                    else -> showToast("Sms permission receiver ditolak")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "com.taufik.androidfundamental.activity.ACTION_DOWNLOAD_STATUS"
        const val SMS_REQUEST_CODE = 101
    }
}