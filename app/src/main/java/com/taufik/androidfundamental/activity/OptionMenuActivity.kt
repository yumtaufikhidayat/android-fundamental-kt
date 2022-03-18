package com.taufik.androidfundamental.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
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