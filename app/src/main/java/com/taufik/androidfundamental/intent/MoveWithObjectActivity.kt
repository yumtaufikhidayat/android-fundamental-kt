package com.taufik.androidfundamental.intent


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.data.Person
import com.taufik.androidfundamental.databinding.ActivityMoveWithObjectBinding

class MoveWithObjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithObjectBinding
    private lateinit var person1: Person
    private lateinit var person2: Person

    companion object {
        const val EXTRA_PERSON_1 = "com.taufik.androidfundamental.intent.EXTRA_PERSON_1"
        const val EXTRA_PERSON_2 = "com.taufik.androidfundamental.intent.EXTRA_PERSON_2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBundle()
        showActionBar()
        setData1()
        setData2()
    }

    private fun setBundle() {
        person1 = intent.getParcelableExtra<Person>(EXTRA_PERSON_1) as Person
        person2 = intent.getParcelableExtra<Person>(EXTRA_PERSON_2) as Person
    }

    private fun showActionBar() {
        supportActionBar?.apply {
            title = person1.title.toString()
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setData1() {
        binding.apply {
            val text = "Nama: ${person1.name}\n" +
                    "Umur: ${person1.age} tahun\n" +
                    "Email: ${person1.email}\n" +
                    "Asal: Kota ${person1.city}"
            tvActivityMoveWithObject.text = text
        }
    }

    private fun setData2() {
        binding.apply {
            val text = "Nama: ${person2.name}\n" +
                    "Umur: ${person2.age} tahun\n" +
                    "Email: ${person2.email}\n" +
                    "Asal: Kota ${person2.city}"
            tvActivityMoveWithObject2.text = text
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}