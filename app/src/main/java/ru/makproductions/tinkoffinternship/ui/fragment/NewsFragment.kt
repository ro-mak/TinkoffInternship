package ru.makproductions.tinkoffinternship.ui.fragment

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.news_fragment.*
import kotlinx.android.synthetic.main.news_fragment.view.*
import ru.makproductions.tinkoffinternship.App
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.presenter.NewsPresenter
import ru.makproductions.tinkoffinternship.view.NewsView

class NewsFragment : MvpAppCompatFragment(), NewsView {

    companion object {
        const val idKey = "id_key"
        fun getInstance(id: Int): NewsFragment {
            val fragment = NewsFragment()
            val args = Bundle()
            args.putInt(idKey, id)
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @ProvidePresenter
    fun providePresenter(): NewsPresenter {
        val presenter = NewsPresenter(AndroidSchedulers.mainThread())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.news_fragment, container, false)
        view.news_content.linksClickable = true
        view.news_content.movementMethod = LinkMovementMethod()

        arguments?.let {
            val id = it.getInt(idKey)
            presenter.loadNewsItem(id)
        }
        return view
    }

    override fun onNewsItemLoaded(content: String) {
        news_content.text = Html.fromHtml(content)
    }
}