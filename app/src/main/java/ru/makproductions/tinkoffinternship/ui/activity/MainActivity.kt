package ru.makproductions.tinkoffinternship.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.makproductions.tinkoffinternship.App
import ru.makproductions.tinkoffinternship.R
import ru.makproductions.tinkoffinternship.navigation.NewsNavigator
import ru.makproductions.tinkoffinternship.navigation.Screens
import ru.makproductions.tinkoffinternship.presenter.main.MainPresenter
import ru.makproductions.tinkoffinternship.ui.fragment.NewsFragment
import ru.makproductions.tinkoffinternship.view.main.MainView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator = NewsNavigator(this, R.id.fragment_container)

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        val presenter = MainPresenter(AndroidSchedulers.mainThread())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.e("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)
        Timber.e("creating fragment! SavedInstance = " + savedInstanceState + " fragment_container = " + fragment_container)
        navigator.applyCommands(arrayOf<Command>(Forward(Screens.Companion.NewsListScreen())))
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is NewsFragment) {
            mainPresenter.navigateToNewsListScreen()
        } else {
            super.onBackPressed()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }


}
