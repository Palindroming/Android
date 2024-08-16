package com.example.ch08

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = com.example.ch08.databinding.Activity3Binding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.visibleBtn.setOnClickListener{
            binding.targetView.visibility = android.view.View.VISIBLE
        }

        binding.invisibleBtn.setOnClickListener{
            binding.targetView.visibility = android.view.View.INVISIBLE
        }


    }
}