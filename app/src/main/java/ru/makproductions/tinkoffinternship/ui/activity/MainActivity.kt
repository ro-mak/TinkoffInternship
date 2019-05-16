package ru.makproductions.tinkoffinternship.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.news_list_fragment.*
import ru.makproductions.tinkoffinternship.App
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.presenter.MainPresenter
import ru.makproductions.tinkoffinternship.ui.adapter.NewsAdapter
import ru.makproductions.tinkoffinternship.view.main.MainView

class MainActivity : AppCompatActivity(), MainView {

    @InjectPresenter
    private lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        val presenter = MainPresenter(AndroidSchedulers.mainThread())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)
        val newsAdapter = NewsAdapter(mainPresenter.getNewsListPresenter())
        news_recycler.adapter = newsAdapter
    }

}