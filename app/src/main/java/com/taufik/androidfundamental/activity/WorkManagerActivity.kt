package com.taufik.androidfundamental.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityWorkManagerBinding
import com.taufik.androidfundamental.manager.MyWorker

class WorkManagerActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWorkManagerBinding.inflate(layoutInflater) }
    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setActionListener()
    }

    private fun setActionListener() = with(binding) {
        btnOneTimeTask.setOnClickListener {
            startOnTimeTask()
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
}