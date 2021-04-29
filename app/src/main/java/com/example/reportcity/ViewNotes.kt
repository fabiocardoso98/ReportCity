package com.example.reportcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.ActionBar

class ViewNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)



        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val titulo: String? = intent.getStringExtra("TITLE")
        val descricao: String? = intent.getStringExtra("DESCRIPTION")
        setTitle(titulo)
        findViewById<EditText>(R.id.titleAddNote).setText(titulo)
        findViewById<EditText>(R.id.descrition).setText(descricao)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}