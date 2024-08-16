package com.example.androidlab


import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity3) }
//        override fun onTouchEvent(event: MotionEvent?): Boolean {
//            when(event?.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    Log.d("KKang", "touch down")
//                }
//                MotionEvent.ACTION_UP -> {
//                    Log.d("KKang", "touch up")
//                }
//            }
//            return super.onTouchEvent(event)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                Log.d("kkang", "Touch down event x : ${event.x}, rawX:${event.rawX}")
            }
        }

            return super.onTouchEvent(event)
            }







//        val textview1 : TextView = findViewById(R.id.text1)

//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
        }

