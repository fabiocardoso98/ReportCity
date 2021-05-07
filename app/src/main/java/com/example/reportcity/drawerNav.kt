package com.example.reportcity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.example.reportcity.ui.reportsOne.reportOne

class drawerNav : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_nav)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)


        val navView: NavigationView = findViewById(R.id.nav_view)

       navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_map, R.id.nav_my_reports, R.id.nav_report, R.id.nav_one_report), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.logout -> {
                val loginShared: SharedPreferences = getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
                loginShared.edit().putInt(getString(R.string.idLogin), 0).commit()
                loginShared.edit().putString(getString(R.string.userLogin), "").commit()
                val intent = Intent(this@drawerNav, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.seeNotes -> {
                val intent = Intent(this@drawerNav, notas::class.java)
                startActivity(intent)
                return true
            }
            R.id.addNoteReport -> {
                val intent = Intent(this@drawerNav, addReport::class.java)
                startActivity(intent)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
