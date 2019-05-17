package ru.makproductions.tinkoffinternship.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.presenter.NewsPresenter
import ru.makproductions.tinkoffinternship.view.NewsView

class NewsFragment : MvpAppCompatFragment(), NewsView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @ProvidePresenter
    fun providePresenter(): NewsPresenter {
        val presenter = NewsPresenter(AndroidSchedulers.mainThread())
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.news_fragment, container, false)
        return view
    }

}