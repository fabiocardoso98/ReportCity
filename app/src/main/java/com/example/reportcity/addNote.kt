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

    private lateinit var tituloView: EditText
    private lateinit var descricaoView: EditText

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

            val id: String? = intent.getStringExtra("ID")
            val titulo: String? = intent.getStringExtra("TITLE")
            val descricao: String? = intent.getStringExtra("DESCRIPTION")

            findViewById<EditText>(R.id.titleAddNote).setText(titulo)
            findViewById<EditText>(R.id.descrition).setText(descricao)


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
        tituloView = findViewById(R.id.titleAddNote)
        descricaoView = findViewById(R.id.descrition)
        val id : Int? = intent.getIntExtra("ID", 0)
        val replyIntent = Intent()

        if (!TextUtils.isEmpty(tituloView.text) && !TextUtils.isEmpty(descricaoView.text) ) {
            if((id != 0)) {
                replyIntent.putExtra("id", id)
                replyIntent.putExtra("titulo", tituloView.text.toString())
                replyIntent.putExtra("descricao", descricaoView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }else{
                Toast.makeText(this, "ID == 0", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_LONG).show()
        }
    }
}