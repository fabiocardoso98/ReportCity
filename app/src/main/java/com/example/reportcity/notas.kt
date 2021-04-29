package com.example.reportcity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
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

        adapter.setOnItemClick(object : NotasAdapter.onItemClick {
            override fun onViewClick(position: Int) {
                val requestCode: Int = 3

                val nota: Notas? = notasViewModel.allNotas.value?.get(position)

                val id: Int? = nota?.id ?: 0
                val titles: String = nota?.title ?: "null"
                val description: String = nota?.description ?: "null"
                val image: String = nota?.image ?: "null"

                val intent = Intent(this@notas, ViewNotes::class.java)
                intent.putExtra("TITLE", titles)
                intent.putExtra("DESCRIPTION", description)
                intent.putExtra("IMAGE", image)
                startActivity(intent)
            }

        })

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                notasViewModel.allNotas.value?.get(viewHolder.adapterPosition)?.id?.let {
                    notasViewModel.deleteNotasOne(
                        it
                    )
                }
            }
        }
        val swipeHandlerEdit = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val requestCode: Int = 2

                val nota: Notas? = notasViewModel.allNotas.value?.get(viewHolder.adapterPosition)
                val id: Int? = nota?.id ?: 0
                val titles: String = nota?.title ?: "null"
                val description: String = nota?.description ?: "null"
                val image: String = nota?.image ?: "null"

                val intent = Intent(this@notas, addNote::class.java)
                intent.putExtra("requestCode", requestCode);
                intent.putExtra("ID", id)
                intent.putExtra("TITLE", titles)
                intent.putExtra("DESCRIPTION", description)
                intent.putExtra("IMAGE", image)
                startActivityForResult(intent,2)

            }
        }

        val itemTouchHelperEdit = ItemTouchHelper(swipeHandlerEdit)
        itemTouchHelperEdit.attachToRecyclerView(recyclerView)

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

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
                val nota = Notas(id = null, title = titulo, description = descricao, image = "img1.png")
                notasViewModel.insert(nota)
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val id: Int? = intentData?.getIntExtra("id", 0)
            val titulo: String? = intentData?.getStringExtra("titulo")
            val descricao: String? = intentData?.getStringExtra("descricao")

            if (id != null && titulo != null && descricao != null) {
                notasViewModel.update(id = id, title = titulo, description = descricao)
            }

        }else if(requestCode == 1){
            Toast.makeText(this, "Erro: Campos Vazios", Toast.LENGTH_SHORT).show()
        }
    }

}