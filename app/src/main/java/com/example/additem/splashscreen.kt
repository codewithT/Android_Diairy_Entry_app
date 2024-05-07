package com.example.additem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()
        Thread {
            try {
                Thread.sleep(2000)  // 3 seconds delay
                val intent = Intent(this, MainActivity::class.java)  // Replace with your main activity
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}