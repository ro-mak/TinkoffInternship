package ru.makproductions.tinkoffinternship.utils

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings
import ru.makproductions.tinkoffinternship.App

object NetworkStatus {
    private val isAirplane: Boolean
        get() = Settings.Global.getInt(
            App.instance.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0

    val status: Status
        get() {
            val cm = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                when (activeNetwork.type) {
                    ConnectivityManager.TYPE_WIFI -> return Status.WIFI
                    ConnectivityManager.TYPE_ETHERNET -> return Status.ETHERNET
                    ConnectivityManager.TYPE_MOBILE -> return Status.MOBILE
                }
                return Status.OTHER
            }
            return Status.OFFLINE
        }

    val isOnline: Boolean
        get() = status != Status.OFFLINE

    val isWifi: Boolean
        get() = status == Status.WIFI

    val isEthernet: Boolean
        get() = status == Status.ETHERNET

    val isMobile: Boolean
        get() = status == Status.MOBILE

    val isOffline: Boolean
        get() = status == Status.OFFLINE

    enum class Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OTHER,
        OFFLINE
    }
}
