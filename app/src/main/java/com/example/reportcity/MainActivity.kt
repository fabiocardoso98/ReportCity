package com.example.reportcity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.addNote).setOnClickListener {
            val intent = Intent(this@MainActivity, notas::class.java)
            startActivity(intent)
        }


    }

}


