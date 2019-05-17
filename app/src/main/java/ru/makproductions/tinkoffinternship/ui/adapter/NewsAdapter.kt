package ru.makproductions.tinkoffinternship.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.news_list_item.view.*
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.presenter.list.NewsAdapterPresenter
import ru.makproductions.tinkoffinternship.view.list.NewsItemView
import timber.log.Timber

class NewsAdapter(private var presenter: NewsAdapterPresenter) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.news_list_item,
                viewGroup,
                false
            ) as LinearLayout
        )
    }

    override fun getItemCount(): Int {
        return presenter.getListCount()
    }

    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        Timber.e("onBindViewHolder = " + position)
        RxView.clicks(viewHolder.itemView).map<NewsViewHolder> { obj -> viewHolder }
            .subscribe(presenter.getClickSubject(position))
        viewHolder.pos = position
        presenter.bindView(viewHolder)
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view), NewsItemView {
        override var pos: Int = 0
        override fun setText(text: String) {
            itemView.news_item_title.text = text
        }

    }

}