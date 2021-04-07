package com.example.reportcity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.application.notasApplication
import com.example.reportcity.entities.Notas
import com.example.reportcity.viewModel.notaViewModel
import com.example.reportcity.viewModel.notaViewModelFactory

class notas : AppCompatActivity() {

    private val newNotaActivityRequestCode = 1
    private val notasViewModel: notaViewModel by viewModels {
        notaViewModelFactory((application as notasApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val adapter = NotasAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notasViewModel.allNotas.observe(this){ notas ->
            notas.let { adapter.submitList(it) }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notas, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.AddNote -> {
                Toast.makeText(this, "Adicionar nota", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, addNote::class.java)
                startActivityForResult(intent,newNotaActivityRequestCode)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val titulo = intentData?.getStringExtra("titulo")
            val descricao= intentData?.getStringExtra("descricao")

            if(titulo != null && descricao != null) {
                val nota = Notas(null,titulo, descricao, "img1.png")
                notasViewModel.insert(nota)
            }
        }else if(requestCode == 1){
            Toast.makeText(this, "Erro: Campos Vazios", Toast.LENGTH_SHORT).show()
        }
    }
}