package com.example.reportcity

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
import com.example.reportcity.viewModel.notaViewModel
import com.example.reportcity.viewModel.notaViewModelFactory

class notas : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
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
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}