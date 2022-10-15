package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityOneTimeAlarmManagerBinding
import com.taufik.androidfundamental.receiver.AlarmReceiver
import com.taufik.androidfundamental.utils.DatePickerFragment
import com.taufik.androidfundamental.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class OneTimeAlarmManagerActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private val binding by lazy { ActivityOneTimeAlarmManagerBinding.inflate(layoutInflater) }
    private val alarmReceiver by lazy { AlarmReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAction()
    }

    private fun setAction() = with(binding) {
        imgBtnOnceDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
        }

        imgBtnOnceTime.setOnClickListener {
            val timePickerFragmentOne = TimePickerFragment()
            timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
        }

        btnSetOnceAlarm.setOnClickListener {
            val onceDate = tvOnceDate.text.toString()
            val onceTime = tvOnceTime.text.toString()
            val onceMessage = etAlarmMessage.text.toString()

            alarmReceiver.setOneTimeAlarm(
                this@OneTimeAlarmManagerActivity,
                AlarmReceiver.TYPE_ONE_TIME,
                onceDate,
                onceTime,
                onceMessage
            )
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.tvOnceDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding.tvOnceTime.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> {}
            else -> {}
        }
    }
}