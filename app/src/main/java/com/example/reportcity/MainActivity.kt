package com.example.reportcity

    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.example.reportcity.api.ServiceBuilder
    import com.example.reportcity.api.endpoints.users
    import com.example.reportcity.api.entities.Users
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response


    class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginShared: SharedPreferences = getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
        val idLogin = loginShared.getInt(getString(R.string.idLogin), 0)
        val userLogin = loginShared.getString(getString(R.string.userLogin), "")

        if((!userLogin.equals("")) && (idLogin != 0)) {
            Toast.makeText(this@MainActivity, "SESSAO INICIADA", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, drawerNav::class.java)
            startActivity(intent)
        }


        findViewById<Button>(R.id.addNote).setOnClickListener {
            val intent = Intent(this@MainActivity, notas::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.login).setOnClickListener {
            val username: String = findViewById<EditText>(R.id.username).text.toString()
            val password: String = findViewById<EditText>(R.id.password).text.toString()

            val request = ServiceBuilder.buildService(users::class.java)
            val call = request.login(username,password)

            call.enqueue(object  : Callback<Users>{
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    if(response.isSuccessful) {

                        Log.d("INICIAR SESSAO", response.body()?.username.toString())

                        loginShared.edit().putInt(getString(R.string.idLogin), response.body()?.id.toString().toInt()).commit()
                        loginShared.edit().putString(getString(R.string.userLogin), response.body()?.username.toString()).commit()
                        val intent = Intent(this@MainActivity, drawerNav::class.java)
                        startActivity(intent)

                        Toast.makeText(this@MainActivity, "INICIAR SESSAO COM SUCESSO", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@MainActivity, "Erro, tente mais tarde!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Log.d("INTERNET LOGIN", t.toString())
                    Toast.makeText(this@MainActivity, "Erro, tente mais tarde!!", Toast.LENGTH_SHORT).show()
                }
            })
        }



    }

}


