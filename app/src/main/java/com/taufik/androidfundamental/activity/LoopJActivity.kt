package com.taufik.androidfundamental.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityLoopjBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class LoopJActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoopjBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoopjBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        getRandomQuote()
        setAction()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Quote of The Day"
    }

    private fun getRandomQuote() = with(binding) {
        pbQuote.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://quote-api.dicoding.dev/random"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                // If connection successful
                when (statusCode) {
                    200 -> {

                        pbQuote.visibility = View.GONE
                        Log.d(TAG, "onSuccess: $responseBody")

                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val quote = responseObject.getString("en")
                        val author = responseObject.getString("author")

                        tvQuote.text = quote
                        tvAuthor.text = author
                    }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                // If connection failed
                val errorMessage = when (statusCode) {
                    400 -> String.format("%s: %s", statusCode, getString(R.string.error400))
                    401 -> String.format("%s: %s", statusCode, getString(R.string.error401))
                    403 -> String.format("%s: %s", statusCode, getString(R.string.error403))
                    404 -> String.format("%s: %s", statusCode, getString(R.string.error404))
                    500 -> String.format("%s: %s", statusCode, getString(R.string.error500))
                    else -> String.format("%s: %s", statusCode, error.message)
                }
                Toast.makeText(this@LoopJActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAction() = with(binding) {
        btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@LoopJActivity, ListQuoteLoopJActivity::class.java))
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}