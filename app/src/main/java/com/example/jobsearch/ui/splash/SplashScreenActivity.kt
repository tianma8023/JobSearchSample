package com.example.jobsearch.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.jobsearch.data.prefs.AppPreferences
import com.example.jobsearch.databinding.ActivitySplashScreenBinding
import com.example.jobsearch.ui.main.MainActivity
import com.example.jobsearch.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        log("JobList", "isFirstLaunch: ${AppPreferences.isFirstLaunch}")
        if (AppPreferences.isFirstLaunch) {
            lifecycleScope.launchWhenCreated {
                delay(5000L)
                withContext(Dispatchers.IO) {
                    AppPreferences.isFirstLaunch = false
                }
                routeToMainActivity()
            }
        } else {
            routeToMainActivity()
        }
    }

    private fun routeToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}