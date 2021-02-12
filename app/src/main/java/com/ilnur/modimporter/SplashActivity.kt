package com.ilnur.modimporter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*Glide.with(this)
            .load(R.drawable.loading_cat)
            .timeout(10)
            .into(findViewById<View>(R.id.loading) as ImageView)*/
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.progress = 0

        lifecycleScope.launch(Dispatchers.Main) {
            //delay(3000)
            while (progressBar.progress != 100){
                delay(30)
                progressBar.progress++
            }

            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(mainIntent)
        }
    }
}