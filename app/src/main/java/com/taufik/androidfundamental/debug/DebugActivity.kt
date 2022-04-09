package com.taufik.androidfundamental.debug

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R

class DebugActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnSetValue: Button
    private lateinit var tvText: TextView

    private var names = ArrayList<String>()

    companion object {
        private const val TAG = "DebugActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        showActionBar()
        setInitView()
        setData()
    }

    private fun showActionBar() {
        supportActionBar?.apply {
            title = "Debugging & Logging"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setInitView() {
        tvText = findViewById(R.id.tvText)
        btnSetValue = findViewById(R.id.btnSetValue)
        btnSetValue.setOnClickListener(this)
    }

    private fun setData() {
        names.add("Narenda Wicaksono")
        names.add("Kevin")
        names.add("Yoza")
        names.add("Saepul")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSetValue -> {
                Log.d(TAG, names.toString())
                val name = StringBuilder()
                for (i in 0..3) {
                    name.append(names[i]).append("\n")
                }
                tvText.text = name.toString()
            }
        }
    }
}