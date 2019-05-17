package ru.makproductions.tinkoffinternship.navigation

import android.support.v4.app.Fragment
import ru.makproductions.tinkoffinternship.ui.fragment.NewsFragment
import ru.makproductions.tinkoffinternship.ui.fragment.NewsListFragment
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : Screen() {
    companion object {
        class NewsScreen(val id: Int) : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return NewsFragment.getInstance(id)
            }
        }

        class NewsListScreen : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return NewsListFragment()
            }
        }
    }
}