package com.example.reportcity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.reportcity.api.ServiceBuilder
import com.example.reportcity.api.endpoints.reports
import com.example.reportcity.api.endpoints.user
import com.example.reportcity.api.entities.result
import com.example.reportcity.api.entities.users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class addReport : AppCompatActivity() {

    var idUser: Int = 0
    var userLocation: String = ""

    private lateinit var nomeView: EditText
    private lateinit var descricaoView: EditText
    private lateinit var moradaView: EditText
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_report)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val loginShared: SharedPreferences = getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
        idUser = loginShared.getInt(getString(R.string.idLogin), 0)
        userLocation= loginShared.getString(getString(R.string.userLocation),"1,2").toString()


        val id: Int? = intent.getIntExtra("id",0)

        if(id != 0) {
            nomeView = findViewById(R.id.addReportName)
            descricaoView = findViewById(R.id.addReportDescription)
            moradaView = findViewById(R.id.addReportAdress)

            val name: String? = intent.getStringExtra("name").toString()
            val description: String? = intent.getStringExtra("description").toString()
            val morada: String? = intent.getStringExtra("morada").toString()

            nomeView.setText(name)
            descricaoView.setText(description)
            moradaView.setText(morada)

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.checkAndAddReport -> {
                val id: Int? = intent.getIntExtra("id",0)

                if(id == 0) {
                    addReport()
                }else{
                    updateReport()
                }
            }
            R.id.cancelCreateReport -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_report_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun addReport() {
        nomeView = findViewById(R.id.addReportName)
        descricaoView = findViewById(R.id.addReportDescription)
        moradaView = findViewById(R.id.addReportAdress)

        Log.d("ADD_REPORT", userLocation)
        Log.d("ADD_REPORT", userLocation!!.split(",")[0].trim().toString())
        Log.d("ADD_REPORT", userLocation!!.split(",")[1].trim().toString())
        Log.d("ADD_REPORT", idUser.toString())

        if (!TextUtils.isEmpty(nomeView.text) && !TextUtils.isEmpty(descricaoView.text)) {
            val request = ServiceBuilder.buildService(reports::class.java)

            val call = request.setReport(nomeView.text.toString(),descricaoView.text.toString(),
                "dsadwasd asdas da ",  userLocation!!.split(",")[0].trim(),
                userLocation!!.split(",")[1].trim(),
                moradaView.text.toString(), idUser.toString())

            call.enqueue(object  : Callback<result> {
                override fun onResponse(call: Call<result>, response: Response<result>) {
                    if(response.isSuccessful) {

                        Toast.makeText(applicationContext, "Situação Adicionada com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{

                        Toast.makeText(applicationContext, "Erro, tente mais tarde!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<result>, t: Throwable) {
                    Log.d("INTERNET LOGIN", t.toString())
                    Toast.makeText(applicationContext, "Erro, tente mais tarde!!", Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            Toast.makeText(applicationContext, "Campos vazios", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateReport() {
        nomeView = findViewById(R.id.addReportName)
        descricaoView = findViewById(R.id.addReportDescription)
        moradaView = findViewById(R.id.addReportAdress)

        val id: Int? = intent.getIntExtra("id", 0)
        val lat: Double? = intent.getDoubleExtra("lat", 0.0)
        val lng: Double? = intent.getDoubleExtra("lng", 0.0)
        val image: String? = intent.getStringExtra("image")
        val users_id: Int? = intent.getIntExtra("users_id", 0)

        if (!TextUtils.isEmpty(nomeView.text) && !TextUtils.isEmpty(descricaoView.text)) {
            val request = ServiceBuilder.buildService(reports::class.java)

            val call = request.setUpdateReport(id!!, nomeView.text.toString(),descricaoView.text.toString(),
                    image!!,  lat.toString(),
                    lng.toString(),
                    moradaView.text.toString(), users_id.toString())

            call.enqueue(object  : Callback<result> {
                override fun onResponse(call: Call<result>, response: Response<result>) {
                    if(response.isSuccessful) {

                        Toast.makeText(applicationContext, "Situação atualizada com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{

                        Toast.makeText(applicationContext, "Erro, tente mais tarde!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<result>, t: Throwable) {
                    Log.d("INTERNET LOGIN", t.toString())
                    Toast.makeText(applicationContext, "Erro, tente mais tarde!!", Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            Toast.makeText(applicationContext, "Campos vazios", Toast.LENGTH_LONG).show()
        }


    }
}