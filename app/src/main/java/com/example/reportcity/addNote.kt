package com.example.reportcity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class addNote : AppCompatActivity() {

    private lateinit var tituloView: EditText
    private lateinit var descricaoView: EditText
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val requestCode: Int = intent.getIntExtra("requestCode", 1)

        if( requestCode == 2) {
            setTitle(R.string.title_activity_edit_note)

            val id: String? = intent.getStringExtra("ID")
            val titulo: String? = intent.getStringExtra("TITLE")
            val descricao: String? = intent.getStringExtra("DESCRIPTION")

            findViewById<EditText>(R.id.titleAddNote).setText(titulo)
            findViewById<EditText>(R.id.descrition).setText(descricao)


        }else{
            setTitle(R.string.title_activity_add_note)

        }

        findViewById<Button>(R.id.btnOpenCam).setOnClickListener {
            Toast.makeText(this, "abrir cam", Toast.LENGTH_LONG).show()
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            filePhoto = getPhotoFile(FILE_NAME)
            val providerFile = FileProvider.getUriForFile(this,"com.example.reportcity.fileprovider", filePhoto)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
            if (takePhotoIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePhotoIntent, 13)
            }else {
                Toast.makeText(this,"Camera could not open", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 13 && resultCode == Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            findViewById<ImageView>(R.id.imgCam).setImageBitmap(takenPhoto)
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        if(requestCode == 13 && resultCode == Activity.RESULT_OK){
            findViewById<ImageView>(R.id.imgCam).setImageURI(data?.data)
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
            replyIntent.putExtra("id", id)
            replyIntent.putExtra("titulo", tituloView.text.toString())
            replyIntent.putExtra("descricao", descricaoView.text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        } else {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_LONG).show()
        }
    }
}