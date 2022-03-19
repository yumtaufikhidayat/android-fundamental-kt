package com.taufik.androidfundamental.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityOptionMenuBinding
import com.taufik.androidfundamental.fragment.MenuFragment

class OptionMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(this@OptionMenuActivity, query, Toast.LENGTH_SHORT).show()
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, MenuFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }

            R.id.menu2 -> {
                startActivity(Intent(this, MenuActivity::class.java))
                true
            }

            else -> true
        }
    }
}