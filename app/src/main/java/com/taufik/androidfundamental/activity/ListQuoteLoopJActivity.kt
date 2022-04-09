package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.adapter.ListQuoteAdapter
import com.taufik.androidfundamental.databinding.ActivityListQuoteLoopjBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class ListQuoteLoopJActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListQuoteLoopjBinding
    private lateinit var listQuoteAdapter: ListQuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuoteLoopjBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initView()
        showData()
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            title = "List Of Quotes"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initView() {
        listQuoteAdapter = ListQuoteAdapter()
        binding.apply {
            with(rvListQuotes) {
                layoutManager = LinearLayoutManager(this@ListQuoteLoopJActivity)
                setHasFixedSize(true)
                adapter = listQuoteAdapter
            }
        }
    }

    private fun showData() = with(binding) {
        pbListQuote.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://quote-api.dicoding.dev/list"
        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                when (statusCode) {
                    200 -> {

                        pbListQuote.visibility = View.GONE

                        val result = String(responseBody)
                        Log.d(TAG, "onSuccess: $result")

                        val listQuotes = ArrayList<String>()
                        val jsonArray = JSONArray(result)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val quote = jsonObject.getString("en")
                            val author = jsonObject.getString("author")
                            listQuotes.add("\n$quote\n - $author\n")
                        }

                        listQuoteAdapter.showListQuotes(listQuotes)
                    }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    400 -> String.format("%s: %s", statusCode, getString(R.string.error400))
                    401 -> String.format("%s: %s", statusCode, getString(R.string.error401))
                    403 -> String.format("%s: %s", statusCode, getString(R.string.error403))
                    404 -> String.format("%s: %s", statusCode, getString(R.string.error404))
                    500 -> String.format("%s: %s", statusCode, getString(R.string.error500))
                    else -> String.format("%s: %s", statusCode, error.message)
                }

                Toast.makeText(this@ListQuoteLoopJActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = ListQuoteLoopJActivity::class.java.simpleName
    }
}