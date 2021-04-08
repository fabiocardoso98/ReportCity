package com.example.reportcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class addNote : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val requestCode: Int = intent.getIntExtra("requestCode", 1)

        if( requestCode == 2) {
            Toast.makeText(this, "EDIT $requestCode", Toast.LENGTH_LONG).show()
            setTitle(R.string.title_activity_edit_note)
        }else{
            Toast.makeText(this, "ADD $requestCode", Toast.LENGTH_LONG).show()
            setTitle(R.string.title_activity_add_note)

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
            R.id.cancelCreateNote -> {
                finish()
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

        if (!TextUtils.isEmpty(titulo.text) && !TextUtils.isEmpty(descricao.text) ) {
            replyIntent.putExtra("titulo", titulo.text.toString())
            replyIntent.putExtra("descricao", descricao.text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        } else {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_LONG).show()
        }
    }


}