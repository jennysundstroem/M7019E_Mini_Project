package com.ltu.m7019e.miniproject.countries


import android.app.Application
import com.ltu.m7019e.miniproject.countries.database.AppContainer
import com.ltu.m7019e.miniproject.countries.database.DefaultAppContainer


class CountriesApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}