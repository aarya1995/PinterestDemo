package com.example.pinterestdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pinterestdemo.ui.main.MainFeedFragment

class MainFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feed)
        getSupportActionBar()?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFeedFragment.newInstance())
                .commitNow()
        }
    }
}
