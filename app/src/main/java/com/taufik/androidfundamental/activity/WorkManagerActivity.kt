package com.taufik.androidfundamental.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityWorkManagerBinding
import com.taufik.androidfundamental.manager.MyWorker
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWorkManagerBinding.inflate(layoutInflater) }
    private val workManager by lazy { WorkManager.getInstance(this) }
    private var periodicWorkRequest: PeriodicWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setActionListener()
    }

    private fun setActionListener() = with(binding) {
        btnOneTimeTask.setOnClickListener {
            startOnTimeTask()
        }

        btnPeriodicTask.setOnClickListener {
            startPeriodicTask()
        }

        btnCancelTask.setOnClickListener {
            cancelPeriodicTask()
        }
    }

    private fun startOnTimeTask() = with(binding) {
        tvStatus.text = getString(R.string.status)
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, etCity.text.toString())
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.apply {
            enqueue(oneTimeWorkRequest)
            getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this@WorkManagerActivity) { workInfo ->
                tvStatus.append("\n" + workInfo.state.name)
            }
        }
    }

    private fun startPeriodicTask() = with(binding) {
        tvStatus.text = getString(R.string.status)
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, etCity.text.toString())
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.apply {
            periodicWorkRequest?.let { periodicWorkRequest ->
                enqueue(periodicWorkRequest)
                getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this@WorkManagerActivity) { workInfo ->
                    tvStatus.append("\n" + workInfo.state.name)
                    btnCancelTask.isEnabled = false
                    if (workInfo.state == WorkInfo.State.ENQUEUED) btnCancelTask.isEnabled = true
                }
            }
        }
    }

    private fun cancelPeriodicTask() {
        periodicWorkRequest?.let {
            workManager.cancelWorkById(it.id)
        }
    }
}