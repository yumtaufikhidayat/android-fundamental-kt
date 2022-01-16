package com.taufik.androidfundamental

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.data.Person
import com.taufik.androidfundamental.databinding.ActivityMainBinding
import com.taufik.androidfundamental.intent.MoveActivity
import com.taufik.androidfundamental.intent.MoveForResultActivity
import com.taufik.androidfundamental.intent.MoveWithDataActivity
import com.taufik.androidfundamental.intent.MoveWithObjectActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMoveActivity()
        setMoveActivityWithData()
        setMoveActivityWithObject()
        setDialANumber()
        setMoveActivityForResult()
    }

    private fun setMoveActivity() {
        binding.apply {
            btnMoveActivity.setOnClickListener {
                startActivity(Intent(this@MainActivity, MoveActivity::class.java))
            }
        }
    }

    private fun setMoveActivityWithData() {
        binding.apply {
            btnMoveActivityWithData.setOnClickListener {
                val intent = Intent(this@MainActivity, MoveWithDataActivity::class.java).apply {
                    putExtra(MoveWithDataActivity.EXTRA_TITLE, "Activity Dengan Data")
                    putExtra(MoveWithDataActivity.EXTRA_AGE, 18)
                    putExtra(MoveWithDataActivity.EXTRA_NAME, "Asep Saepudin")
                }
                startActivity(intent)
            }
        }
    }

    private fun setMoveActivityWithObject() {
        binding.apply {
            btnMoveActivityWithObject.setOnClickListener {
                val person1 = Person(
                    "Pindah Activity Object",
                    "Asep Saepudin",
                    18,
                    "asep@gmail.com",
                    "Bandung"
                )

                val person2 = Person(
                    "Pindah Activity Object",
                    "Zharfan Wafiq",
                    20,
                    "wafiq@gmail.com",
                    "Jambi"
                )

                val intent = Intent(this@MainActivity, MoveWithObjectActivity::class.java).apply {
                    putExtra(MoveWithObjectActivity.EXTRA_PERSON_1, person1)
                    putExtra(MoveWithObjectActivity.EXTRA_PERSON_2, person2)
                }
                startActivity(intent)
            }
        }
    }

    private fun setDialANumber() {
        binding.apply {
            btnDialANumber.setOnClickListener {
                val phoneNumber = "085296257704"
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")))
            }
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            val text = "Hasil: $selectedValue"

            binding.apply {
                tvResult.text = text
            }
        }
    }

    private fun setMoveActivityForResult() {
        binding.apply {
            btnMoveActivityForResult.setOnClickListener {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }
}
