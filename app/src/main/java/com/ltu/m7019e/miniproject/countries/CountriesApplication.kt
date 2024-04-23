package com.ltu.m7019e.miniproject.countries

import com.ltu.m7019e.miniproject.countries.database.AppContainer
import com.ltu.m7019e.miniproject.countries.database.DefaultAppContainer

class CountriesApplication {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}