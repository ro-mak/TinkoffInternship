package ru.makproductions.tinkoffinternship.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.news_list_fragment.*
import kotlinx.android.synthetic.main.news_list_fragment.view.*
import ru.makproductions.tinkoffinternship.App
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.presenter.list.NewsListPresenter
import ru.makproductions.tinkoffinternship.ui.adapter.NewsAdapter
import ru.makproductions.tinkoffinternship.view.list.NewsListView
import timber.log.Timber

class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    @InjectPresenter
    lateinit var presenter: NewsListPresenter

    @ProvidePresenter
    fun providePresenter(): NewsListPresenter {
        val presenter = NewsListPresenter(AndroidSchedulers.mainThread())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.e("OnCreateView")
        val view = inflater.inflate(R.layout.news_list_fragment, container, false)
        setupRecycler(view)
        setHasOptionsMenu(true)
        return view
    }

    private fun setupRecycler(view: View) {
        val linearLayoutManager = LinearLayoutManager(activity)
        view.news_recycler.layoutManager = linearLayoutManager
        val newsAdapter = NewsAdapter(presenter.newsAdapterPresenterImpl)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        view.news_recycler.addItemDecoration(dividerItemDecoration)
        view.news_recycler.adapter = newsAdapter
        view.news_swiperefresh.setOnRefreshListener({
            Timber.e("Refresh by swipe")
            presenter.refreshNews()
        })
    }

    override fun onAttach(context: Context?) {
        Timber.e("onAttach")
        super.onAttach(context)
    }

    override fun onNewsLoaded() {
        Timber.e("onNewsLoaded")
        news_recycler.adapter?.notifyDataSetChanged()
        news_swiperefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.news_list_action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.menu_refresh) {
            Timber.e("Refresh by button")
            presenter.refreshNews()
        }
        return super.onOptionsItemSelected(item)
    }
}