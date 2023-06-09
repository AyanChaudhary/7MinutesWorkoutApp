package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener{
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)

        }
        binding?.flBmi?.setOnClickListener{
            val intent=Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }
        binding?.flHistory?.setOnClickListener{
            startActivity(Intent(this,HistoryActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}