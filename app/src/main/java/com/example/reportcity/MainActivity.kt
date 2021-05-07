package com.example.reportcity

    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.util.Log
    import android.widget.*
    import androidx.appcompat.app.AppCompatActivity
    import androidx.fragment.app.FragmentTransaction
    import com.example.reportcity.api.ServiceBuilder
    import com.example.reportcity.api.endpoints.user
    import com.example.reportcity.api.entities.users
    import com.example.reportcity.ui.reportsOne.reportOne
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.userLoginImg).setImageResource(R.drawable.account_circle_24)

        val loginShared: SharedPreferences = getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
        val idLogin = loginShared.getInt(getString(R.string.idLogin), 0)
        val userLogin = loginShared.getString(getString(R.string.userLogin), "")

        if((!userLogin.equals("")) && (idLogin != 0)) {
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
            val check: Boolean = findViewById<CheckBox>(R.id.checkboxSaveSession).isChecked

            Toast.makeText(this@MainActivity, "SUCESSO: $check", Toast.LENGTH_LONG).show()

            val request = ServiceBuilder.buildService(user::class.java)
            val call = request.login(username,password)

            call.enqueue(object  : Callback<users>{
                override fun onResponse(call: Call<users>, response: Response<users>) {
                    if(response.isSuccessful) {

                            loginShared.edit().putInt(getString(R.string.idLogin), response.body()?.id.toString().toInt()).commit()
                            loginShared.edit().putString(getString(R.string.userLogin), response.body()?.username.toString()).commit()

                        val intent = Intent(this@MainActivity, drawerNav::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this@MainActivity, "Erro, tente mais tarde!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<users>, t: Throwable) {
                    Log.d("INTERNET LOGIN", t.toString())
                    Toast.makeText(this@MainActivity, "Erro, tente mais tarde!!", Toast.LENGTH_SHORT).show()
                }
            })
        }



    }

    override  fun onBackPressed() {

    }


}


