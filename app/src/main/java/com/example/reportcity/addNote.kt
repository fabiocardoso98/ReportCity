package com.example.reportcity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.reportcity.application.notasApplication
import com.example.reportcity.entities.Notas
import com.example.reportcity.viewModel.notaViewModel
import com.example.reportcity.viewModel.notaViewModelFactory

class addNote : AppCompatActivity() {

    private val notasViewModel: notaViewModel by viewModels {
        notaViewModelFactory((application as notasApplication).repository)
    }

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
                Toast.makeText(this, "Adicionar nota", Toast.LENGTH_LONG).show()
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


        if (titulo.text.toString().isNotEmpty()) {
            Log.d("ADD NOTE", titulo.text.toString())
            Log.d("ADD NOTE", descricao.text.toString())

            val notas1 = Notas(1, "Titulo", "sfgijsfasd d auSDN Ads ", "adsasasd")


            Log.d("ADD NOTE 2", titulo.text.toString())
            Log.d("ADD NOTE 2", descricao.text.toString())

            notasViewModel.insert(notas1)
            Log.d("ADD NOTE 3", titulo.text.toString())
            Log.d("ADD NOTE 3", descricao.text.toString())
        } else {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_LONG).show()
        }
    }


}