package com.example.reportcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.reportcity.application.notasApplication
import com.example.reportcity.entities.Notas
import com.example.reportcity.viewModel.notaViewModel
import com.example.reportcity.viewModel.notaViewModelFactory

class addNote : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.checkAndAddNote -> {
                //Toast.makeText(this, "Adicionar nota", Toast.LENGTH_LONG).show()
                addNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notas_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun addNote() {
        val titulo: EditText = findViewById<EditText>(R.id.titleAddNote)
        val descricao: EditText = findViewById<EditText>(R.id.descrition)
        val replyIntent = Intent()

        if (titulo.text.toString().isNotEmpty() || descricao.text.toString().isNotEmpty() ) {
            replyIntent.putExtra("titulo", titulo.text.toString())
            replyIntent.putExtra("descricao", descricao.text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
        } else {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_CANCELED, replyIntent)
        }
        finish()
    }


}