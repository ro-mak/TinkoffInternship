package ru.makproductions.tinkoffinternship.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.ui.adapter.NewsAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsAdapter = NewsAdapter()
        news_recycler.adapter = newsAdapter
    }
}
