package com.amarydev.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amarydev.movieapp.R
import com.amarydev.movieapp.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie, R.id.navigation_tv
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_favorite -> {
                val splitInstallManager = SplitInstallManagerFactory.create(this)
                val moduleFavorite = "favorite"
                if (splitInstallManager.installedModules.contains(moduleFavorite)){
                    startActivity(Intent(this, Class.forName("com.amarydev.movieapp.favorite.FavoriteActivity")))
                } else {
                    val request = SplitInstallRequest.newBuilder()
                        .addModule(moduleFavorite)
                        .build()

                    splitInstallManager.startInstall(request)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}